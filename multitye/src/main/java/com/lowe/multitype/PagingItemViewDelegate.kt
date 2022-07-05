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
 * @author Lowae
 */
abstract class PagingItemViewDelegate<T, VH : ViewHolder>:ItemViewBaseDelegate<T, VH>() {

  /**
   * This is a [PagingMultiTypeAdapter] like PagingDataAdapter
   */
  @Suppress("PropertyName")
  internal var _adapter: PagingMultiTypeAdapter<*>? = null

  /**
   * Gets the associated [PagingMultiTypeAdapter].
   * @since v2.3.4
   */
  val adapter: PagingMultiTypeAdapter<*>
    get() {
      if (_adapter == null) {
        throw IllegalStateException(
          "This $this has not been attached to PagingMultiTypeAdapter yet. " +
              "You should not call the method before registering the delegate."
        )
      }
      return _adapter!!
    }
}
