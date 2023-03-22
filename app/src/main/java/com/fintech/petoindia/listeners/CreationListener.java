package com.fintech.petoindia.listeners;

import com.fintech.petoindia.data.model.PackageType;
import com.fintech.petoindia.data.model.PersonUserModel;
import com.fintech.petoindia.data.model.UserTypeModel;

import java.util.List;

public interface CreationListener {

    void achieveUserType(List<UserTypeModel> userTypeModelList);
    void achievePersonUser(List<PersonUserModel> personUserModelList);
    void achievePackageType(List<PackageType> packageTypeList);

}
