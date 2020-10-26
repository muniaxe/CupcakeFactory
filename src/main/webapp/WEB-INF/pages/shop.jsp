<%--
  Created by IntelliJ IDEA.
  User: Windowshoi
  Date: 21/10/2020
  Time: 11.36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Start shopping</h1>
<form>
    <div class="row">
        <div class="form-group col-5">
            <label class="w-100">Vælg bund
                <select class="form-control" required>
                    <option>Chokolade</option>
                    <option>Blåbær</option>
                </select>
            </label>
        </div>
        <div class="form-group col-5">
            <label class="w-100">Vælg topping
                <select class="form-control" required>
                    <option>Chokolade</option>
                    <option>Blåbær</option>
                </select>
            </label>
        </div>
        <div class="form-group col-2">
            <label class="w-100">Vælg antal
                <input type="number" class="form-control" max="100" required>
            </label>
        </div>
    </div>
    <div class="d-flex justify-content-end">
        <input class="btn btn-primary" type="submit" value="Bestil">
    </div>

</form>
