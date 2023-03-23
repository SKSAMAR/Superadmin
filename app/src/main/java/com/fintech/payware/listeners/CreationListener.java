package com.fintech.payware.listeners;

import com.fintech.payware.data.model.PackageType;
import com.fintech.payware.data.model.PersonUserModel;
import com.fintech.payware.data.model.UserTypeModel;

import java.util.List;

public interface CreationListener {

    void achieveUserType(List<UserTypeModel> userTypeModelList);
    void achievePersonUser(List<PersonUserModel> personUserModelList);
    void achievePackageType(List<PackageType> packageTypeList);

}
