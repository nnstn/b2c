package com.pursuit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUITreeNode;
import com.pursuit.common.util.HttpClientUtil;
import com.pursuit.mapper.TbContentCategoryMapper;
import com.pursuit.pojo.TbContentCategory;
import com.pursuit.pojo.TbContentCategoryExample;
import com.pursuit.pojo.TbContentCategoryExample.Criteria;
import com.pursuit.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Value("${REST_BASE_URL}")
	private String  REST_BASE_URL;
	@Value("${REST_ITEMCAT_URL}")
	private String  REST_ITEMCAT_URL;
	@Value("${REST_SYNC}")
	private String  REST_SYNC;
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EasyUITreeNode> getContentCategory(Long parentId) {
		//1.执行查询
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//2.拼装返回结果
		List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
		for (int i = 0; i < list.size(); i++) {
			TbContentCategory cc = list.get(i);
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(cc.getId());
			node.setText(cc.getName());
			node.setState(cc.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}
	@Override
	public B2CResult createContentCategory(Long parentId, String nodeName) {
		//拼装请求
		TbContentCategory cc = new TbContentCategory();
		cc.setCreated(new Date());
		cc.setUpdated(new Date());
		cc.setIsParent(false);
		//'排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数'
		cc.setSortOrder(1);
		//1(正常),2(删除)
		cc.setStatus(1);
		
		cc.setName(nodeName);
		cc.setParentId(parentId);
		//插入数据
		contentCategoryMapper.insert(cc);
		Long id = cc.getId();
		//如果父节点是叶子节点则更新为父节点
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			parent.setUpdated(new Date());
			contentCategoryMapper.updateByPrimaryKeySelective(parent);
		}
		//同步缓存
		syncItemCat();
		return B2CResult.ok(id);
	}
	@Override
	public B2CResult updateContentCategory(Long id, String nodeName) {
		TbContentCategory cc = new TbContentCategory();
		cc.setId(id);
		cc.setName(nodeName);
		cc.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKeySelective(cc);
		//同步缓存
		syncItemCat();
		return B2CResult.ok();
	}
	@Override
	public B2CResult deleteContentCategory(Long id) {
		//1.执行删除
		TbContentCategory cc = contentCategoryMapper.selectByPrimaryKey(id);
		if(cc.getIsParent()){
			deleteCatalogNode(id);
		}else{
			contentCategoryMapper.deleteByPrimaryKey(id);
		}
		//2.如果父节点被删空--则改变其属性
		Long parentId = cc.getParentId();
		List<TbContentCategory> childList = getChildList(parentId);
		if(childList.size()==0){
			TbContentCategory record = new TbContentCategory();
			record.setId(parentId);
			record.setIsParent(false);
			record.setUpdated(new Date());
			contentCategoryMapper.updateByPrimaryKeySelective(record);
		}
		//同步缓存
		syncItemCat();
		return B2CResult.ok();
	}
	
	private void deleteCatalogNode(Long id) {
		//执行查询
		List<TbContentCategory> list = getChildList(id);
		
		for (TbContentCategory tbContentCategory : list) {
			if(tbContentCategory.getIsParent()){
				//目录节点--进行递归
				deleteCatalogNode(tbContentCategory.getId());
			}else{
				//叶子节点--删除节点
				contentCategoryMapper.deleteByPrimaryKey(id);
			}
		}
	}
	private List<TbContentCategory> getChildList(Long parentId){
		//1.查询当前节点所有子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//2.执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		return list;
	}
	private void syncItemCat(){
		HttpClientUtil.doGet(REST_BASE_URL+REST_ITEMCAT_URL+REST_SYNC);
	}
}
