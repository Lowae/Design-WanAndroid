package com.lowe.wanandroid.utils

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf

private const val PACKAGE_NAME = "com.lowe.wanandroid"

interface AddressableActivity {

    val className: String

    val bundle: Bundle
}

fun intentTo(addressable: AddressableActivity) =
    Intent().setComponent(ComponentName(PACKAGE_NAME, addressable.className))
        .putExtras(addressable.bundle)

object Activities {

    object Setting: AddressableActivity {
        override val className: String
            get() = "$PACKAGE_NAME.ui.setting.SettingActivity"
        override val bundle: Bundle
            get() = bundleOf()
    }

    object Login : AddressableActivity {

        override val className: String
            get() = "$PACKAGE_NAME.ui.login.LoginActivity"

        override val bundle: Bundle
            get() = bundleOf()
    }

    class ShareList(
        override val className: String = "$PACKAGE_NAME.ui.share.ShareListActivity",
        override val bundle: Bundle
    ) : AddressableActivity {

        companion object {
            const val KEY_SHARE_LIST_USER_ID = "key_share_list_user_id"
        }

    }

    class Web(
        override val className: String = "$PACKAGE_NAME.ui.web.WebActivity",
        override val bundle: Bundle
    ) : AddressableActivity {

        companion object {
            const val KEY_WEB_VIEW_URL = "key_web_view_url"
        }
    }

}