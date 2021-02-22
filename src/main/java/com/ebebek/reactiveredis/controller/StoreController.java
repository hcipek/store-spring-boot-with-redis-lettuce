package com.ebebek.reactiveredis.controller;

import com.ebebek.reactiveredis.model.store.Store;
import com.ebebek.reactiveredis.model.store.StoreRequest;
import com.ebebek.reactiveredis.model.store.StoreResponse;
import com.ebebek.reactiveredis.model.ui.store.UIStoreRequest;
import com.ebebek.reactiveredis.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/redis/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping("/createStore")
    public StoreResponse createStore(@RequestBody UIStoreRequest request) {
        return storeService.createStore(request);
    }

    /**
     * Value Operations For Redis
     * getAll from another project and sets it to Certain key as HashMap
     * onSave when a data inserted another project sends data to this method and sets the value
     * onUpdate when a data updated another project sends data to this method and sets the value
     */

    @GetMapping("/getAll")
    public List<Store> getAll() {
        return storeService.getAll();
    }

    @PostMapping("/onSave")
    public StoreResponse onSave(@RequestBody StoreRequest request) {
        return storeService.onSave(request);
    }

    @PostMapping("/onUpdate")
    public StoreResponse onUpdate(@RequestBody StoreRequest request) { return storeService.onUpdate(request); }

    /**
     * Hash Operations For Redis
     * getAll from another project and puts it to Certain key as HashMap
     * onSave when a data inserted another project sends data to this method and puts the value
     * onUpdate when a data updated another project sends data to this method and puts the value
     */

    //Puts all data to a Map with keys and puts that map into another key
    @GetMapping("/getAllHash")
    public Collection<Store> getAllHash() {
        return storeService.getAllHash();
    }

    @PostMapping("/onSaveHash")
    public StoreResponse onSaveHash(@RequestBody StoreRequest request) {
        return storeService.onSaveHash(request);
    }

    @PostMapping("/onUpdateHash")
    public StoreResponse onUpdateHash(@RequestBody StoreRequest request) {
        return storeService.onUpdateHash(request);
    }

    @GetMapping("/getByHashKey")
    public Store getByHashKey(@RequestParam Long id) {
        return storeService.getByHashKey(id);
    }

    /**
     * List Operations For Redis
     *
     *
     */

    @GetMapping("/getAllList")
    public List<Store> getAllList() {
        return storeService.getAllList();
    }

    @GetMapping("/getListByPaging")
    public List<Store> getListByPaging(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize) {
        return storeService.getListByPaging(pageIndex, pageSize);
    }

}