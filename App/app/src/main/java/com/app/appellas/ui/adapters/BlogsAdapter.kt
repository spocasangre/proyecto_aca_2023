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
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.appellas.R
import com.app.appellas.data.models.dtos.response.BlogsResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class BlogsAdapter: RecyclerView.Adapter<BlogsAdapter.BlogsViewHolder>() {

    private var blogsList: List<BlogsResponse>? = null

    inner class BlogsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BlogsAdapter.BlogsViewHolder {
        val card = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_blog, parent, false)

        return BlogsViewHolder(card)
    }

    override fun onBindViewHolder(holder: BlogsAdapter.BlogsViewHolder, position: Int) {
        val blog = blogsList?.get(position)
        val uri = "http://api-app-ellas.us-east-1.elasticbeanstalk.com/files/"+ blog?.imagenes?.get(0)?.src
        holder.itemView.findViewById<TextView>(R.id.item_blog_title).text = blog?.titulo
        holder.itemView.findViewById<TextView>(R.id.item_blog_subtitle).text = blog?.subtitulo
        holder.itemView.findViewById<TextView>(R.id.item_blog_description).text = blog?.descripcion
        val image = holder.itemView.findViewById<ImageView>(R.id.item_blog_image)

        Glide.with(holder.itemView.context)
            .load(uri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(image)

        /*holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections
                .actionHomeFragment2ToDetailPassFragment(generatedPass?.id.toString())
            it.findNavController().navigate(direction)
        }*/
    }

    override fun getItemCount(): Int = blogsList?.size ?: 0

    fun setData(list: List<BlogsResponse>) {
        blogsList = list
        notifyDataSetChanged()
    }
}