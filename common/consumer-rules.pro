# Model
-keep class com.lowe.common.services.model.** {*;}
-keep class com.lowe.common.model.** {*;}
-keepclasseswithmembers class com.lowe.common.base.http.adapter.NetworkResponse {*;}
-keepclasseswithmembers class * extends com.lowe.common.base.http.adapter.NetworkResponse {*;}
-keepclasseswithmembers class com.lowe.common.account.AccountState {*;}
-keepclasseswithmembers class com.lowe.common.account.LocalUserInfo {*;}
-keepclasseswithmembers class com.lowe.common.account.RegisterInfo {*;}