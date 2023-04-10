package com.fintech.scnpay.listeners;

import com.fintech.scnpay.data.model.PackageType;
import com.fintech.scnpay.data.model.PersonUserModel;
import com.fintech.scnpay.data.model.UserTypeModel;

import java.util.List;

public interface CreationListener {

    void achieveUserType(List<UserTypeModel> userTypeModelList);
    void achievePersonUser(List<PersonUserModel> personUserModelList);
    void achievePackageType(List<PackageType> packageTypeList);

}
