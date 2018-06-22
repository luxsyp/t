package john.snow.api.model

abstract class RMCharacterResponse {
    abstract fun info(): RMCharacterResponseInfo?
//    abstract fun results(): List<RMCharacter>?
}

abstract class RMCharacterResponseInfo {
    abstract fun count(): Int
    abstract fun next(): String
}

abstract class RMCharacter {
    abstract fun id(): Int
    abstract fun name(): String
    abstract fun status(): String
    abstract fun image(): String
}