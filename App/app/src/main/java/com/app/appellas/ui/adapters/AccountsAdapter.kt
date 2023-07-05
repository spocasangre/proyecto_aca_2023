/*
  Copyright 2023 WeGotYou!

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package com.app.appellas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.appellas.R
import com.app.appellas.data.models.dtos.response.UserResponse

class AccountsAdapter(private val deleteHandler: (Int) -> Unit): RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {

    private var userList: List<UserResponse>? = ArrayList()

    private var rol: String = ""

    inner class AccountsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        val card = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account, parent, false)

        return AccountsViewHolder(card)
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val user = userList?.get(position)
        holder.itemView.findViewById<TextView>(R.id.item_account_name).text = user?.nombre
        holder.itemView.findViewById<Button>(R.id.item_account_action_delete).setOnClickListener {
            deleteHandler(user!!.id)
        }
    }

    override fun getItemCount() = userList?.size ?: 0

    fun setData(list: List<UserResponse>) {
        userList = list
        notifyDataSetChanged()
    }

}