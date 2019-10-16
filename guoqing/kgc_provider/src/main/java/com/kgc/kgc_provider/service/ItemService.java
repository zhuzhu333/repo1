package com.kgc.kgc_provider.service;

import com.kgc.kgc_provider.model.ItemKillSuccess;
import com.kgc.kgc_provider.model.item;

import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/11 15:21
 * @since
 */
public interface ItemService {
    long isExist(Long id);
    int insertKillItem(ItemKillSuccess itemKillSuccess);
    int cutStock(Long id);
    void KillItem(ItemKillSuccess itemKillSuccess);
    List<item> showItems(item item1);

}
