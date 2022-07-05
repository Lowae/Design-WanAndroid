/*
 * Copyright (c) 2016-present. Drakeet Xu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lowe.multitype

import androidx.recyclerview.widget.RecyclerView.ViewHolder

/***
 * @author Drakeet Xu
 * @since v4.1.0
 */
abstract class ItemViewDelegate<T, VH : ViewHolder> : ItemViewBaseDelegate<T, VH>(){

  @Suppress("PropertyName")
  internal var _adapter: MultiTypeAdapter? = null

  /**
   * Gets the associated [MultiTypeAdapter].
   * @since v2.3.4
   */
  val adapter: MultiTypeAdapter
    get() {
      if (_adapter == null) {
        throw IllegalStateException(
          "This $this has not been attached to MultiTypeAdapter yet. " +
              "You should not call the method before registering the delegate."
        )
      }
      return _adapter!!
    }

  /**
   * Gets or sets the items of the associated [MultiTypeAdapter].
   * @see MultiTypeAdapter.items
   * @since v4.0.0
   */
  var adapterItems: List<Any>
    get() = adapter.items
    set(value) {
      adapter.items = value
    }
}
