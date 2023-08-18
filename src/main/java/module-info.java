module de.cuioss.uimodel {

    requires de.cuioss.java.tools;
    requires static lombok;

    exports de.cuioss.uimodel.application;
    exports de.cuioss.uimodel.field;
    exports de.cuioss.uimodel.field.impl;
    exports de.cuioss.uimodel.model;
    exports de.cuioss.uimodel.model.code;
    exports de.cuioss.uimodel.model.conceptkey;
    exports de.cuioss.uimodel.model.conceptkey.impl;
    exports de.cuioss.uimodel.model.impl;
    exports de.cuioss.uimodel.result;
    exports de.cuioss.uimodel.nameprovider;
    exports de.cuioss.uimodel.nameprovider.data;
    exports de.cuioss.uimodel.service;

}