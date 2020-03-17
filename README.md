# BaseRecyclerViewAdapter for DataBinding

DataBinding을 이용하면 사용하기 편한 RecyclerView Adapter를 소개합니다.

# BaseViewHolder


    open class BaseViewHolder<T : Any>(
        val viewDataBinding: ViewDataBinding
    ) : RecyclerView.ViewHolder(viewDataBinding.root) {
    
        val view = viewDataBinding.root
    
        open fun onBind(item: T?) {
            tryCatch {
                viewDataBinding.setVariable(BR.item, item)
                viewDataBinding.executePendingBindings()
            }
        }
    }

`BaseViewHolder`에 `setVariable`를 `item`으로 명시하여 layout에서 `item`으로 `Data Class`를 받을 수 있게 했다.

# BaseRecyclerViewAdapter

소스는 하단의 GitHub에서 확인하면 된다.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
    
        require(layoutId > 0) { "Empty Layout Resource" }
    
        **val dataBinding = createViewDataBinding(parent, layoutId)
        val viewHolder = createViewHolder(dataBinding)**
    
        dataBinding.run {
            // onClick
            **itemClickListener?.let { listener ->
                root.run {
    								if (isCircleRipple) addCircleRipple() else addRipple()
                    setOnClickListener { view ->
                        if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                            listener.invoke(view, list[viewHolder.adapterPosition])
                        }
                    }
                }
            }**
        }
        return viewHolder
    }

`createViewDataBinding`, `createViewHolder`에서 각각 `ViewDataBinding`과 `ViewHolder`를 생성한다.

`dataBinding.root`(View)를 클릭 이벤트로 설정하여 `itemClickListener`에  `View`와 `Data`를 invoke한다.
또한 View Extension으로 Ripple을 적용하였다. 아래 링크에서 확인가능하다.

[View Extension으로 Ripple 효과 적용하기](https://www.notion.so/View-Extension-Ripple-b6c6f94d600344d6b1c6335f38a96f2a)

    /**
     * 아이템 클릭 리스너
     *
     * @param listener
     * @param isCircleRipple
     */
    open fun setOnItemClickListener(
        listener: ((View, T?) -> Unit)
    ) {
        this.itemClickListener = listener
    }

Item 클릭 리스너 설정, `View`와 `Data`를 `invoke`할 수 있게 설정했다.

# Layouts


    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:bind="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools">
    
        <data>
    
            <variable
                name="viewModel"
                type="com.jess.base.presenter.main.MainViewModel" />
    
        </data>
    
        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context="com.jess.base.presenter.main.MainActivity">
    
            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/rv_main"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                bind:items="@{viewModel.list}"
                tools:listitem="@layout/main_item" />
        </androidx.constraintlayout.widget.ConstraintLayout>
    </layout>

main_activity.xml

    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:bind="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools">
    
        <data>
    
            <variable
                name="item"
                type="com.jess.base.data.MainData" />
    
        </data>
    
        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="wrap_content"
            android:layout_height="match_parent">
    
            <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/tv_title"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:gravity="center"
                android:text="@{item.title}"
                app:layout_constraintLeft_toLeftOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                tools:text="제목" />
        </androidx.constraintlayout.widget.ConstraintLayout>
    </layout>

main_item.xml

`BaseViewHolder`에서 `item`으로 setVariable했기 때문에 `variable name`을 item으로 설정하고 type을 `Data Class`로 설정한다.

# BindingAdapter


    /**
     * RecyclerView Adapter
     *
     * @param items
     * @param isClear
     */
    @BindingAdapter(value = ["items", "isClear"], requireAll = false)
    fun RecyclerView.addAllItem(
        items: List<Any>?,
        isClear: Boolean = true
    ) {
        tryCatch {
            (this.adapter as? BaseRecyclerViewAdapter<Any, ViewDataBinding>)?.run {
                if (isClear) {
                    this.clear()
                }
    
                if (!items.isNullOrEmpty()) {
                    this.addAllItem(items)
                }
            }
        }
    }

`main_activity.xml`에서 `bind:items="@{viewModel.list}"`를 적용하면 `@BindingAdapter`로 호출된다.

# 사용방법


    rv_main.adapter = object :
            BaseRecyclerViewAdapter<MainData, MainItemBinding>(
                R.layout.main_item
            ) {
        }.apply {
    					setOnItemClickListener { view, data ->           
            }
        }

`onBindData`가 필요 없는 경우

    rv_main.adapter = object :
            BaseRecyclerViewAdapter<MainData, MainItemBinding>(
                R.layout.main_item
            ) {
    
            override fun onBindData(
                position: Int,
                data: MainData?,
                dataBinding: MainItemBinding
            ) {
               
            }
        }.apply {
    					setOnItemClickListener { view, data ->           
            }
        }

`onBindData`가 필요한 경우
