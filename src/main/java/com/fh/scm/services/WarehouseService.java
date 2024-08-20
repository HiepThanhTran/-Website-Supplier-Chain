package com.fh.scm.services;

import com.fh.scm.pojo.Warehouse;

import java.util.List;
import java.util.Map;

public interface WarehouseService {

    Warehouse get(Long id);

    void insert(Warehouse warehouse);

    void update(Warehouse warehouse);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Warehouse warehouse);

    Long count();

    List<Warehouse> getAll(Map<String, String> params);
}
