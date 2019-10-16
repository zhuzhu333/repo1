package com.kgc.kgc_provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.kgc_provider.mapper.ItemKillSuccessMapper;
import com.kgc.kgc_provider.mapper.itemMapper;
import com.kgc.kgc_provider.model.ItemKillSuccess;
import com.kgc.kgc_provider.model.item;
import com.kgc.kgc_provider.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/11 15:22
 * @since
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private itemMapper mapper;

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Override
    public long isExist(Long id) {
        item item1 = mapper.selectByPrimaryKey(id);
        return item1.getStock();

    }

    @Override
    public int insertKillItem(ItemKillSuccess itemKillSuccess) {
        return itemKillSuccessMapper.insert(itemKillSuccess);
    }

    @Override
    public int cutStock(Long id) {

        return mapper.cutStock(id);
    }
    @Transactional
    @Override
    public void KillItem(ItemKillSuccess itemKillSuccess) {
        //生成订单表(秒杀成功中间表)

        itemKillSuccessMapper.insert(itemKillSuccess);
        //减库存
        mapper.cutStock(itemKillSuccess.getId());

    }

    @Override
    public List<item> showItems(item item1) {

        return mapper.getAllitems(item1);
    }
}
