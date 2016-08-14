package com.pursuit.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.search.pojo.SearchItem;
import com.pursuit.search.mapper.ItemMapper;
import com.pursuit.search.service.ItemService;

@Service("itemService")
public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	@Override
	public B2CResult getItemList() throws Exception {
		//查询数据库获得商品列表
		List<SearchItem> itemList = itemMapper.getItemList();
		
		for (SearchItem searchItem : itemList) {
			//创建文档对象
			SolrInputDocument doc = new SolrInputDocument();
			//添加域
			doc.addField("id", searchItem.getId());
			doc.addField("item_title", searchItem.getTitle());
			doc.addField("item_sell_point", searchItem.getSellPoint());
			doc.addField("item_price", searchItem.getPrice());
			doc.addField("item_image", searchItem.getImage());
			doc.addField("item_category_name", searchItem.getCategoryName());
			doc.addField("item_desc", searchItem.getItemDesc());
			//写入索引库
			solrServer.add(doc);
			//solrServer.deleteById(searchItem.getId());
		}
		//提交
		solrServer.commit();
		return B2CResult.ok();
	}
	
	@Override
	public B2CResult update(Long id) throws Exception{
		//删除原有索引
		solrServer.deleteById(id+"");
		
		SearchItem searchItem = itemMapper.getItemById(id);
		if(null!=searchItem&&null!=searchItem.getId()){
			//创建文档对象
			SolrInputDocument doc = new SolrInputDocument();
			//添加域
			doc.addField("id", searchItem.getId());
			doc.addField("item_title", searchItem.getTitle());
			doc.addField("item_sell_point", searchItem.getSellPoint());
			doc.addField("item_price", searchItem.getPrice());
			doc.addField("item_image", searchItem.getImage());
			doc.addField("item_category_name", searchItem.getCategoryName());
			doc.addField("item_desc", searchItem.getItemDesc());
			//写入索引库
			solrServer.add(doc);
			solrServer.commit();
			return B2CResult.ok();
		}
		return B2CResult.build(500, "查询无此商品");
	}

}
