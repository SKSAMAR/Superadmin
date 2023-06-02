package com.fintech.superadmin.clean.domain.model.contact

data class Contact(val name: String?, val phoneNumber: String?, val isAvailableOnWhatsApp: Boolean? = false){

    override fun toString(): String {
        return "Contact(name='$name', phoneNumber='$phoneNumber', isAvailableOnWhatsApp=$isAvailableOnWhatsApp)"
    }
}
