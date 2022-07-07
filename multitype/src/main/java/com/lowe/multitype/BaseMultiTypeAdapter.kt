package com.lowe.multitype

import android.util.Log
import android.view.ViewGroup
import androidx.annotation.CheckResult
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

/**
 * [MultiTypeAdapter] or [PagingMultiTypeAdapter] base implements, used for abstract main functions.
 *
 * @author Lowae
 */
abstract class BaseMultiTypeAdapter @JvmOverloads constructor(
    open val initialCapacity: Int = 0,
    open var types: Types = MutableTypes(initialCapacity),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * [PagingMultiTypeAdapter] need this
     */
    abstract fun getItem(position: Int): Any

    /**
     * Registers a type class and its item view delegate. If you have registered the class,
     * it will override the original delegate(s). Note that the method is non-thread-safe
     * so that you should not use it in concurrent operation.
     *
     * Note that the method should not be called after
     * [RecyclerView.setAdapter], or you have to call the setAdapter
     * again.
     *
     * @param clazz the class of a item
     * @param delegate the item view delegate
     * @param T the item data type
     * */
    fun <T> register(clazz: Class<T>, delegate: ItemViewBaseDelegate<T, *>) {
        unregisterAllTypesIfNeeded(clazz)
        register(Type(clazz, delegate, DefaultLinker()))
    }

    inline fun <reified T : Any> register(delegate: ItemViewBaseDelegate<T, *>) {
        register(T::class.java, delegate)
    }

    inline fun <reified T : Any> register(
        // Keep this parameter to provide the explicit relationship
        @Suppress("UNUSED_PARAMETER") clazz: KClass<T>,
        delegate: ItemViewBaseDelegate<T, *>,
    ) {
        // Always use the reified type to avoid javaPrimitiveType problem
        // See https://github.com/drakeet/MultiType/issues/302
        register(T::class.java, delegate)
    }

    fun <T> register(clazz: Class<T>, binder: ItemViewBinder<T, *>) {
        register(clazz, binder as ItemViewBaseDelegate<T, *>)
    }

    fun <T> register(clazz: Class<T>, binder: PagingItemViewBinder<T, *>) {
        register(clazz, binder as ItemViewBaseDelegate<T, *>)
    }

    inline fun <reified T : Any> register(binder: ItemViewBinder<T, *>) {
        register(binder as ItemViewBaseDelegate<T, *>)
    }

    inline fun <reified T : Any> register(binder: PagingItemViewBinder<T, *>) {
        register(binder as ItemViewBaseDelegate<T, *>)
    }

    inline fun <reified T : Any> register(clazz: KClass<T>, binder: ItemViewBinder<T, *>) {
        register(clazz, binder as ItemViewBaseDelegate<T, *>)
    }

    inline fun <reified T : Any> register(clazz: KClass<T>, binder: PagingItemViewBinder<T, *>) {
        register(clazz, binder as ItemViewBaseDelegate<T, *>)
    }

    internal fun <T> register(type: Type<T>) {
        types.register(type)
        when (type.delegate) {
            is ItemViewDelegate -> {
                check(this is MultiTypeAdapter) { IllegalArgumentException("Are you register ${type.delegate} to MultiTypeAdapter correctly?") }
                type.delegate._adapter = this
            }
            is PagingItemViewDelegate -> {
                check(this is PagingMultiTypeAdapter<*>) { IllegalArgumentException("Are you register ${type.delegate} to PagingMultiTypeAdapter correctly?") }
                type.delegate._adapter = this
            }
        }
    }

    /**
     * Registers a type class to multiple item view delegates. If you have registered the
     * class, it will override the original delegate(s). Note that the method is non-thread-safe
     * so that you should not use it in concurrent operation.
     *
     * Note that the method should not be called after
     * [RecyclerView.setAdapter], or you have to call the setAdapter again.
     *
     * @param clazz the class of a item
     * @param <T> the item data type
     * @return [OneToManyFlow] for setting the delegates
     * @see [register]
     */
    @CheckResult
    fun <T> register(clazz: Class<T>): OneToManyFlow<T> {
        unregisterAllTypesIfNeeded(clazz)
        return OneToManyBuilder(this, clazz)
    }

    @CheckResult
    fun <T : Any> register(clazz: KClass<T>): OneToManyFlow<T> {
        return register(clazz.java)
    }

    /**
     * Registers all of the contents in the specified [Types]. If you have registered a
     * class, it will override the original delegate(s). Note that the method is non-thread-safe
     * so that you should not use it in concurrent operation.
     *
     * Note that the method should not be called after
     * [RecyclerView.setAdapter], or you have to call the setAdapter
     * again.
     *
     * @param types a [Types] containing contents to be added to this adapter inner [Types]
     * @see [register]
     * @see [register]
     */
    fun registerAll(types: Types) {
        val size = types.size
        for (i in 0 until size) {
            val type = types.getType<Any>(i)
            unregisterAllTypesIfNeeded(type.clazz)
            register(type)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return indexInTypesOf(position, getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        indexViewType: Int
    ): RecyclerView.ViewHolder {
        return types.getType<Any>(indexViewType).delegate.onCreateViewHolder(parent.context, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        getOutDelegateByViewHolder(holder).onBindViewHolder(holder, getItem(position), payloads)
    }

    /**
     * Called to return the stable ID for the item, and passes the event to its associated delegate.
     *
     * @param position Adapter position to query
     * @return the stable ID of the item at position
     * @see ItemViewBaseDelegate.getItemId
     * @see RecyclerView.Adapter.setHasStableIds
     * @since v3.2.0
     */
    override fun getItemId(position: Int): Long {
        val itemViewType = getItemViewType(position)
        return types.getType<Any>(itemViewType).delegate.getItemId(getItem(position))
    }

    /**
     * Called when a view created by this adapter has been recycled, and passes the event to its
     * associated delegate.
     *
     * @param holder The ViewHolder for the view being recycled
     * @see RecyclerView.Adapter.onViewRecycled
     * @see ItemViewBaseDelegate.onViewRecycled
     */
    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        getOutDelegateByViewHolder(holder).onViewRecycled(holder)
    }

    /**
     * Called by the RecyclerView if a ViewHolder created by this Adapter cannot be recycled
     * due to its transient state, and passes the event to its associated item view delegate.
     *
     * @param holder The ViewHolder containing the View that could not be recycled due to its
     * transient state.
     * @return True if the View should be recycled, false otherwise. Note that if this method
     * returns `true`, RecyclerView *will ignore* the transient state of
     * the View and recycle it regardless. If this method returns `false`,
     * RecyclerView will check the View's transient state again before giving a final decision.
     * Default implementation returns false.
     * @see RecyclerView.Adapter.onFailedToRecycleView
     * @see ItemViewBaseDelegate.onFailedToRecycleView
     */
    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return getOutDelegateByViewHolder(holder).onFailedToRecycleView(holder)
    }

    /**
     * Called when a view created by this adapter has been attached to a window, and passes the
     * event to its associated item view delegate.
     *
     * @param holder Holder of the view being attached
     * @see RecyclerView.Adapter.onViewAttachedToWindow
     * @see ItemViewBaseDelegate.onViewAttachedToWindow
     */
    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        getOutDelegateByViewHolder(holder).onViewAttachedToWindow(holder)
    }

    /**
     * Called when a view created by this adapter has been detached from its window, and passes
     * the event to its associated item view delegate.
     *
     * @param holder Holder of the view being detached
     * @see RecyclerView.Adapter.onViewDetachedFromWindow
     * @see ItemViewBaseDelegate.onViewDetachedFromWindow
     */
    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        getOutDelegateByViewHolder(holder).onViewDetachedFromWindow(holder)
    }

    private fun getOutDelegateByViewHolder(holder: RecyclerView.ViewHolder): ItemViewBaseDelegate<Any, RecyclerView.ViewHolder> {
        @Suppress("UNCHECKED_CAST")
        return types.getType<Any>(holder.itemViewType).delegate as ItemViewBaseDelegate<Any, RecyclerView.ViewHolder>
    }

    private fun getOutDelegateByViewHolder(position: Int): ItemViewBaseDelegate<Any, RecyclerView.ViewHolder> {
        @Suppress("UNCHECKED_CAST")
        return types.getType<Any>(getItemViewType(position)).delegate as ItemViewBaseDelegate<Any, RecyclerView.ViewHolder>
    }

    @Throws(DelegateNotFoundException::class)
    internal fun indexInTypesOf(position: Int, item: Any): Int {
        val index = types.firstIndexOf(item.javaClass)
        if (index != -1) {
            val linker = types.getType<Any>(index).linker
            return index + linker.index(position, item)
        }
        throw DelegateNotFoundException(item.javaClass)
    }

    private fun unregisterAllTypesIfNeeded(clazz: Class<*>) {
        if (types.unregister(clazz)) {
            Log.w(TAG, "The type ${clazz.simpleName} you originally registered is now overwritten.")
        }
    }

    companion object {
        private const val TAG = "MultiTypeAdapter"
    }
}