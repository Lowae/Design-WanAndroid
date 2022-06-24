package com.lowe.wanandroid.base.app

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lowe.wanandroid.services.CommonService
import java.lang.reflect.InvocationTargetException
import javax.inject.Inject

class AppViewModelFactory @Inject constructor(
    private val application: Application,
    private val commonService: CommonService
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != AppViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class, must be ${AppViewModel::class.java}")
        }
        return try {
            modelClass.getConstructor(Application::class.java, CommonService::class.java)
                .newInstance(application, commonService)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } as T
    }
}