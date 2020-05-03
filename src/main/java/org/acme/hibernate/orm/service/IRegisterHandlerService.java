package org.acme.hibernate.orm.service;

import io.vertx.core.json.JsonObject;

import java.util.List;

public interface IRegisterHandlerService {

    void register(JsonObject body);

    void addSession(Integer key, String user);

    List<String> addNewUsers(String user);

}
