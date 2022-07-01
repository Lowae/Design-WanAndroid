# 🦄Material Design WanAndroid
## 界面：
App内通篇全采用[Material Design 3](https://m3.material.io/)风格，拒绝半完成式Material带来的UI的割裂感。<p>
所有Icon取自[Material Symbols](https://fonts.google.com/icons)，统一而规范的设计。<p>
主题色遵循[Material3 Color system](https://m3.material.io/styles/color/the-color-system/key-colors-tones)。
- PrimaryColor, On-primary, Primary container, On-primary container
- SecondaryColor 同上
- TertiaryColor 
<p>

采用[Material Theme Builder](https://material-foundation.github.io/material-theme-builder/)从图片取色而成。<p>
实现[Dynamic Colors](https://m3.material.io/styles/color/dynamic-color/overview)，App主题色自动跟随系统主题色，保持一贯的视觉体验(Android 12及以上支持)

所以可交互的UI均带有Ripple效果，明确表示这是个可交互控件，且Ripple颜色支持取自当前Dynamic colors的主题色

## 逻辑：
全Kotlin化，gradle配合buildSrc，实现全局且统一的依赖管理。<p>
严格遵循[MVVM架构](https://developer.android.com/topic/architecture)，逻辑分为：
- 界面层(UI Layer)
	- APP内实现：视图（Activity/Fragment等） + 数据驱动及处理逻辑的状态容器（ViewModel等）
- 网域层(Domain Layer) 可选项，用于处理复杂逻辑或支持可重用性吗，当你需要从不同数据源获取数据时如需要同时从数据库和接口请求数据时，推荐使用UseCase进行组合。
	- App内实现：组合数据源（UseCase）
- 数据层(Data Layer)
	- App内实现：数据源（Repository）

当你采用某项东西，应是为了解决某些特定的问题，不能单纯为了用而用。在该架构下：<p>
- 对于网络请求的需要，引入通用的网络请求库，[Retrofit](https://github.com/square/retrofit) + [OkHttp](https://github.com/square/okhttp)。<p>
- 针对数据层Repository需要以及UseCase需要复用并组合各类Service，引入[Hilt](https://developer.android.com/training/dependency-injection/hilt-android)，解决依赖注入问题，提高可重用性且避免强依赖。
- 对于网络请求的线程切换使用[Kotlin协程](https://developer.android.com/kotlin/coroutines?hl=zh-cn)，针对复杂且需要进行各类转换处理的数据流使用[Flow](https://developer.android.com/kotlin/flow?hl=zh-cn)，对于轻量数据且需要和Lifecycle相关的数据使用[LiveData](https://developer.android.com/topic/libraries/architecture/livedata?hl=zh-cn)。
- 对于App内的部分需要持久化数据如Cookie、KV数据等小型数据引入[DataStore](https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn)
- 对于RecyclerView引入[Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=zh-cn)列表的加载及状态处理
- 针对列表的多类型Item，导入并修改[MultiType](https://github.com/drakeet/MultiType)使其支持配合Paging3使用
- 对于UI与数据之间的单向或双向绑定使用[DataBinding](https://developer.android.com/topic/libraries/data-binding?hl=zh-cn)减少重复、模板代码

除以上主要依赖外，其余功能均自己实现。
