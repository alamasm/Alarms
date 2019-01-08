package Server

data class SimpleTime(var hours: Int, var minutes: Int) {
    override fun toString(): String {
        return "$hours:$minutes"
    }
}