# RxJava2Operator
RxJava2 中常用操作符代码练习及说明。  
*ReactiveX 系列所有操作符以及RxJava2与RxJava1的操作符变化可查看 [ReactiveX 操作符](http://reactivex.io/documentation/operators.html)*

# 项目中主要包含的操作符展示
## 创建操作符
* create()：通过调用观察者的方法从头创建一个Observable
* just()：将对象或者对象集合转换为一个会发射这些对象的Observable
* fromXxx()系列：`fromArray()`/`fromIterable()`/`fromFuture()`等；将其它的对象、数字或列表数据结构转换为Observable
* empty()/never()/error()：创建行为受限的特殊Observable
* range()：创建发射指定范围的 **整数** 序列的Observable,`range()`操作符,发射从 start 开始的 count 个数
* defer()：在观察者订阅之前不创建这个Observable，当被订阅时为每一个观察者创建一个新的Observable
* repat()：**作用在Observable上**,会对其重复发射count次，当没指定次数时，将一直不停的发射
* timer()：在指定时间后发射一个数字0，注意其默认运行在RxJava的 computation 线程，可以指定运行的线程
* interval()：间隔一段时间发射一个整数，整数从0开始每发射，后面的一个数就在原来的基础上加1。  
注意其默认运行在RxJava的 computation 线程，可以指定运行的线程，同时又多个重载方法，还可以指定发射第一个数之前的延迟时间
* intervalRange()：和 `interval()` 操作符类似，不同的是可以指定数字的开始大小和一共发射的个数，可以指定线程，默认在 RxJava 的 computation 线程
## 变换操作符
* map()：通过对序列的每一项都应用一个函数变换Observable发射的数据，实质是对序列中的每一项执行一个函数，函数的参数就是这个数据项
* flatMap()：对Observable发射的数据都应用(apply)一个函数，这个函数返回一个Observable，然后合并这些Observables，并且发送合并的结果，异步时不能保证顺序不变
* concatMap()：对Observable发射的数据都应用(apply)一个函数，这个函数返回一个Observable，然后合并这些Observables，并且发送合并的结果，异步时能保证顺序不变
* reduce()：对Observable发射的每一项数据应用一个函数，但是只发射最终的值，与 `scan()` 操作符做比较
* scan()：对Observable发射的每一项数据应用一个函数，然后按顺序依次发射这些值,与  `reduce()` 操作符做比较
* buffer()：可以简单的理解为缓存，它定期从Observable收集数据到一个集合，然后把这些数据集合打包发射，而不是一次发射一个，有多个重载方法，可指定延迟时间、间隔时间、线程等
* groupBy()：将原来的Observable分拆为Observable集合，将原始Observable发射的数据按Key分组，每一个Observable发射一组不同的数据，有多个重载方法
* window()：定期将来自Observable的数据分拆成一些Observable窗口，然后发射这些窗口，而不是每次发射一项，有多个重载方法
## 过滤操作符
* filter()：过滤掉没有通过谓词测试的数据项，只发射通过测试的
* distinct()：过滤掉重复数据项
* elementAt()：取特定位置的数据项
* debounce()：只有在空闲了一段时间后才发射数据，通俗的说，就是如果一段时间没有操作，就执行一次操作.还可以根据一个函数来进行限流。这个函数的返回值是一个临时Observable， 如果源Observable在发射一个新的数据的时候，上一个数据根据函数所生成的临时Observable还没有结束,没有调用onComplete，那么上一个数据就会被过滤掉。如果是最后一个,还是会发射
* ignoreElements()：忽略所有的数据，只保留终止通知(onError或onCompleted)
* sample()：定期发射最新的数据，等于是数据抽样。多个重载方法，可指定线运行所在线程等，默认 RxJava的 computation 线程
* first()/firstElement()：只发射满足条件的第一条数据
* last()}/lastElement()：只发射最后一条数据
* skip()}/skipLast()：跳过前面/最后 count 项 ，多个重载方法，可设置延迟时间和运行线程，默认非主线程
* take()}/takeLast()：只取前面/最后 count 项 ，多个重载方法，可设置延迟时间和运行线程，默认非主线程
## 组合操作符
* merger()：将两个后多个Observable/Iterable发射的数据组合并成一个，Merge 操作符可能会让合并的Observables发射的数据交错
* concat()：将两个后多个Observable/Iterable发射的数据组合并成一个，Concat 操作符不会让合并的Observables发射的数据交错，它会按顺序一个接着一个发射多个Observables的发射项
* zip()：使用一个指定的函数将多个Observable发射的数据组合在一起，然后将这个函数的结果作为单项数据发射。类似操作符 `zipIterable()`/`zipArray()`
	* ① Zip操作符将多个Observable发射的数据按顺序组合起来，每个数据只能组合一次，而且都是有序的;
    * **② 最终组合的数据的数量由发射数据最少的Observable来决定**
* join()：无论何时，如果一个Observable发射了一个数据项，只要在另一个Observable发射的数据项定义的时间窗口内，就将两个Observable发射的数据合并发射.
	* ① 源Observable.join(所要组合的目标Observable)；
	* ② 第一个参数，接收从源Observable发射来的数据，并返回一个Observable，这个Observable的生命周期决定了源Observable发射出来数据的有效期；
	* ③ 第二个参数，接收从目标Observable发射来的数据，并返回一个Observable，这个Observable的生命周期决定了目标Observable发射出来数据的有效期；
	* ④ 第三个参数，接收从源Observable和目标Observable发射来的数据，并返回最终组合完的数据。
* combineLatest()：当两个Observables中的任何一个发射了一个数据时，通过一个指定的函数组合每个Observable发射的最新数据（一共两个数据），然后发射这个函数的结果.  
比如：Observable1发射了A并且Observable2发射了B和C，`combineLatest()`将会分组处理AB和AC。  
	* 必须满足的两个条件:
		* ① 所有的Observable都发射过数据；
		* ② 满足条件1的时候任何一个Observable发射一个数据，就将所有Observable最新发射的数据按照提供的函数组装起来发射出去。
	* 注意：
		* **在这两个条件下,可能会忽略掉一些发射的数据.**
* switchOnNext()/switchMap()：将一个发射Observable序列的Observable转换为这样一个Observable：它逐个发射那些Observable最近发射的数据。用来将一个发射多个小Observable的源Observable转化为一个Observable，然后发射这多个小Observable所发射的数据。
	*  **需要注意的就是，如果一个小的Observable正在发射数据的时候，源Observable又发射出一个新的小Observable，则前一个Observable发射的数据会被抛弃，直接发射新的小Observable所发射的数据。**
* startWith()：在发射原来的Observable的数据序列之前，先发射一个指定的数据序列或数据项(在数据序列的开头插入一条指定的项)。  
     * 这个指定的项可以是单个的发射项；也可以是数组、列表或者一个Observable。
* and()/then()/when()：通过模式(And条件)和计划(Then次序)组合两个或多个Observable发射的数据集
	* 注意：
     * ① 需要导入包含 and/then/when 操作符的库 **`compile 'io.reactivex:rxjava-joins:0.22.0`**
     * ② 同时 Observable 和  Observer 也要使用 `rx.Observabl`e 和 `rx.Observer` 包下的。
## 错误处理操作符
* onErrorReturn()：用返回的字符串替换错误的项，然后调用 `onComplete()`方法
* onErrorResumeNext(Function)：创建一个新的 Observable 发射，发射完新的 Observable 中的所有数据后调用 `onComplete()`方法
* onErrorResumeNext(Observable)：创建一个新的 Observable ，使用新的 Observable 继续发射数据，数据项发射完成不会自动调用 `onComplete()`方法
* onErrorReturnItem()：用指定的字符串替换错误的项，然后调用 `onComplete()`方法
* onExceptionResumeNext()：`onExceptionResumeNext()` 操作符区分异常类型
	* ① 如果抛出的是 `Exception` ,会先发射新的 Observable 的所有项后调用 `onComplete()` 方法
	* ② 如果抛出的是 `Throwable` ，那么会直接调用 `onError()` 方法
* retry()：在发生错误的时候会重新进行订阅,而且可以重复多次，所以发射的数据可能会产生重复
	* 有多个重载方法，可以指定重复次数、判断是否需要重复等
    * **如果重复指定次数还有错误的话就会将错误返回给观察者,会调用 `onError()` 方法**
* retryWhen()：当错误发生时，`retryWhen()`会接收`onError()`的`throwable`作为参数，并根据定义好的函数返回一个Observable，如果这个Observable发射一个数据，就会重新订阅这个Observable
## 辅助操作符
* delay()：延迟一段时间发射结果数据。多个重载方法，可以指定运行线程、出错时是否延迟发送等
* doXxx()系列：Do操作符就是给Observable的生命周期的各个阶段加上一系列的回调监听，当Observable执行到这个阶段的时候，这些回调就会被触发
* subscribeOn()：SubscribeOn：指定Observable应该在哪个调度程序上执行；ObserveOn：指定Subscriber的调度程序（工作线程）
* observeOn()：ObserveOn：指定Subscriber的调度程序（工作线程）；SubscribeOn：指定Observable应该在哪个调度程序上执行
* materialize()：Meterialize 操作符将 `OnNext`/`OnError`/`OnComplete` 都转化为一个 Notification 对象并按照原来的顺序发射出来
* dematerialize()：与Meterialize 操作符相反
* timeInterval()：TimeInterval会拦截发射出来的数据，取代为前后两个发射两个数据的间隔时间。对于第一个发射的数据，其时间间隔为订阅后到首次发射的间隔。多个重载，可以指定时间单位和线程
* timestamp()：TimeStamp会将每个数据项给重新包装一下，加上了一个时间戳来标明每次发射的时间。多个重载，可以指定时间单位和线程
* timeout()：Timeout操作符给Observable加上超时时间，每发射一个数据后就重置计时器，当超过预定的时间还没有发射下一个数据，就抛出一个超时的异常。有多个重载，可以自定义更多的功能
* using()：创建一个只在Observable的生命周期内存在的一次性资源
	* 参数说明：
		* ① 创建这个一次性资源的函数
		* ② 创建Observable的函数
		* ③ 释放资源的函数
## 条件和布尔操作符
* all()：判断Observable发射的所有的数据项是否都满足某个条件
* amb()：给定多个Observable，只让第一个发射数据的Observable发射全部数据
* contains()：判断Observable是否会发射一个指定的数据项
* sequenceEqual()：判断两个Observable是否按相同的数据序列。有多个重载方法。【判断两个Observable发射的数据序列是否相同**（发射的数据相同，数据的序列相同，结束的状态相同）**，如果相同返回 true，否则返回 false】
* isEmpty()：操作符用来判断源 Observable 是否发射过数据，没有发射过数据返回true
* defaultIfEmpty()：操作符会判断源 Observable 是否发射数据，如果源 Observable 发射了数据则正常发射这些数据，如果没有则发射一个默认的数据
* skipUntil()：根据一个标志 Observable 来判断的，当这个标志 Observable **没有发射数据的时候，所有源 Observable 发射的数据都会被跳过； 当标志Observable发射了一个数据，则开始正常地发射数据**
* skipWhile()：根据一个函数来判断是否跳过数据， **当函数返回值为 true 的时候则一直跳过源 Observable 发射的数据；当函数返回 false 的时候则开始正常发射数据**
	*  **注意：skipWhile() 操作符根据函数来判断是否跳过，当函数返回 true 时，就每发射一次数据都会执行函数，并且跳过当前数据；当函数返回了 false 时，那么后面就会正常发射数据(包括当前数据也会正常发射)，不在执行这个函数了**
* takeUntil()：根据一个标志 Observable 来判断的， **当这个标志 Observable 发射了数据的时候，所有源 Observable 发射的数据都会被跳过；当标志 Observable 没有发射一个数据的时候，则正常地发射数据**
* takeWhile()：根据一个函数来判断是否跳过数据， **当函数返回值为 true 的时候则正常发射源 Observable 的数据；当函数返回 false 的时候将不在发射任何数据**
	*  **注意：takeWhile() 操作符根据函数来判断是否跳过，当函数返回 true 时，就每发射一次数据都会执行函数，并且数据发送成功；当函数返回了 false 时，那么就会跳过所有的数据了(包括当前的数据也会跳过)，并且不在执行这个函数了**
## 算术和聚合操作符
* count()：Count 操作符用来统计源 Observable 发射了多少个数据，最后将数目给发射出来
     * 如果源 Observable 发射错误，则会将错误直接报出来；在源 Observable 没有终止前，count 是不会发射统计数据的。
* collect()：Collect 用来将源 Observable 发射的数据给收集到一个数据结构里面，需要使用两个参数：
	* ① 第一个产生收集数据结构的函数；
	* ② 第二个接收第一个函数产生的数据结构和源Observable发射的数据作为参数的函数
* concat()：将两个后多个Observable/Iterable发射的数据组合并成一个
	* Concat 操作符不会让合并的Observables发射的数据交错，它会按顺序一个接着一个发射多个Observables的发射项 与 `merge()` 操作符对比查看
* reduce()：对Observable发射的每一项数据应用一个函数，但是只发射最终的值，`scan()` 操作符做比较
## 连接操作符
### 什么是可连接的 Observable(Connectable Observable)：
> 可连接的 Observable 是一种特殊的 Observable 对象，并不是 订阅(Subscrib) 的时候就发射数据，而是只有对其调用 `connect()` 操作符的时候才开始发射数据，所以可以用来更灵活的控制数据发射的时机。

* publish()：将一个普通的 Observable 对象转化为一个可连接的(Connectable Observable)。需要注意的是如果发射数据已经开始了再进行订阅只能接收以后发射的数据
* connect()：Connect操作符就是用来触发 Connectable Observable 发射数据的
	* 调用 `connect()` 操作符后会返回一个 Subscription 对象，通过这个 Subscription 对象，我们可以调用其 `unsubscribe()` 方法来终止数据的发射；另外，即使还没有订阅者订阅的时候就调用 `connect()` 操作符也是可以使其开始发射数据的(只要调用了 `connect()` 方法就会开始发射数据，不管是否有订阅者订阅的事件)。
* refCount()：是将一个 Connectable Observable 对象再重新转化为一个普通的 Observable 对象，这时候订阅者进行订阅时就会触发数据的发射
* replay()：返回一个 Connectable Observable 对象并且可以缓存其发射过的数据，这样即使有订阅者在其发射数据之后进行订阅也能收到其之前发射过的数据
	* <b>注意：
     * ① 使用 `replay()` 操作符我们最好还是限定其缓存的大小，否则缓存的数据太多了可会占用很大的一块内存，有多个重载方法可以指定缓存大小、运行线程的。
     * ② `replay()` 操作符直接返回一个 Connectable Observable 对象,不用在调用 `publish()` 操作符</b>
## 转换操作符
* toXxx()：将 Observable 转换为其它的对象或数据结构（`toList()`、`toMap()`、`toMultimap()`、...）
