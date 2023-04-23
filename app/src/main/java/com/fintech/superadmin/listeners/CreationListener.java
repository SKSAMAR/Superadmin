package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.model.PackageType;
import com.fintech.superadmin.data.model.PersonUserModel;
import com.fintech.superadmin.data.model.UserTypeModel;

import java.util.List;

public interface CreationListener {

    void achieveUserType(List<UserTypeModel> userTypeModelList);
    void achievePersonUser(List<PersonUserModel> personUserModelList);
    void achievePackageType(List<PackageType> packageTypeList);

}
