package com.fintech.paytcash.listeners;

import com.fintech.paytcash.data.model.PackageType;
import com.fintech.paytcash.data.model.PersonUserModel;
import com.fintech.paytcash.data.model.UserTypeModel;

import java.util.List;

public interface CreationListener {

    void achieveUserType(List<UserTypeModel> userTypeModelList);
    void achievePersonUser(List<PersonUserModel> personUserModelList);
    void achievePackageType(List<PackageType> packageTypeList);

}
