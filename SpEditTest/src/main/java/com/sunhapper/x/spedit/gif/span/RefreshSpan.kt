package com.sunhapper.x.spedit.gif.span

import com.sunhapper.x.spedit.gif.drawable.InvalidateDrawable

/**
 * Created by sunhapper on 2019/1/25 .
 */
interface RefreshSpan {

    fun getInvalidateDrawable(): InvalidateDrawable?
}
