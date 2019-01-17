package fr.mbds.tp

class Message {

    String messageContent

    Date dateCreated

    User author

    boolean isDelete

    static constraints = {
        messageContent nullable:false, bank:false
        author nullable:true
    }
}
