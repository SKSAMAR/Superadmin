package com.fintech.webspidysoftware.listeners;

import com.fintech.webspidysoftware.data.model.PackageType;
import com.fintech.webspidysoftware.data.model.PersonUserModel;
import com.fintech.webspidysoftware.data.model.UserTypeModel;

import java.util.List;

public interface CreationListener {

    void achieveUserType(List<UserTypeModel> userTypeModelList);
    void achievePersonUser(List<PersonUserModel> personUserModelList);
    void achievePackageType(List<PackageType> packageTypeList);

}
