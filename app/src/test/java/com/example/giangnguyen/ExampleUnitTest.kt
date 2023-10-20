package com.example.giangnguyen

import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    fun getParamsFromURL(urlString: String): HashMap<String, String> {
        val queryString = urlString.substring(urlString.indexOf("?") + 1)
        val parameters = HashMap<String, String>();
        if (queryString.trim().isNotEmpty()) {
            queryString.split("&").forEach { pair ->
                val equals = pair.indexOf('=')
                val key = if (equals > 0) pair.substring(0, equals) else pair;
                val value = if (equals > 0) pair.substring(equals + 1) else ""
                parameters[key] = value
            }
        }
        return parameters

    }

    @Test
    fun getParamTest() {
        val params =
            getParamsFromURL("https://stackoverflow.com/questions/71177106/how-to-implement-iterator-for-a-linkedlist-using-kotlin?a=1&b=2&c=3")
        assertEquals(params["a"], "1")
    }

    fun getResponses(
        valid_auth_tokens: Array<String>,
        requests: Array<Array<String>>
    ): Array<String> {
        // Write your code here
        val result = mutableListOf<String>()
        for (request in requests) {
            val requestParams = getParamsFromURL(request[1])
            val token = requestParams["token"]
            var params = ""
            if (valid_auth_tokens.contains(token)) {
                requestParams.forEach { key, value ->
                    if (key != "token") {
                        val param = "$key,$value"
                        if (params.isEmpty()) {
                            params += params
                        } else {
                            params += ",$param"
                        }
                    }
                }
                result += "VALID,$params"
            } else {
                result += "INVALID"
            }
        }
        return result.toTypedArray()
    }


    @Test
    fun addition_isCorrect() {
        val a = 888.8.toInt()
        val xyz = 100.toByte().toLong()
        val t = 100L.toByte().toInt()
        val t1 = 100L.toByte()
        assertEquals(4, 2 + 2)
    }

    fun findCharacterAsInt(startAt: Int, encoded: String): Int {
        val remainingString = encoded.substring(startAt)
        val length = remainingString.length

        if (length >= 2) {
            val firstTwo = remainingString.substring(0, 2).toInt()
            if (firstTwo >= 32 || length == 2) {
                return firstTwo
            } else {
                val firstThree = remainingString.substring(0, 3).toInt()
                return firstThree
            }
        }
        return remainingString.toInt()
    }

    @Test
    fun test_decode() {
        val encodedString = "23511011501782351112179911801562340161171141148"
        val revertedEncodedString = encodedString.split("").reversed().joinToString(separator = "")

        val result = decode(revertedEncodedString)

        println("result: $result")
    }


    fun decode(encoded: String): String {
        val length = encoded.length
        val intArray = mutableListOf<Int>()
        var startAt = 0
        while (startAt < length) {
            val characterInt = findCharacterAsInt(startAt, encoded)
            val characterLength = characterInt.toString().length
            startAt += characterLength
            intArray += characterInt
        }
        var result: String = ""
        intArray.forEach {
            val character = it.toChar()
            result += character
        }
        return result

    }

    fun deleteEven(listHead: SinglyLinkedListNode?): SinglyLinkedListNode? {
        // Write your code here
        val result = SinglyLinkedList()

        listHead?.let {
            var currentNode: SinglyLinkedListNode? = it

            while (currentNode != null) {
                if (currentNode.data % 2 != 0) {
                    result.insertNode(currentNode.data)
                }
                currentNode = currentNode.next
            }
        }
        return result.head


    }


}

class SinglyLinkedListNode(nodeData: Int) {
    public var data: Int
    public var next: SinglyLinkedListNode?

    init {
        data = nodeData
        next = null
    }
}

class SinglyLinkedList {
    public var head: SinglyLinkedListNode?
    public var tail: SinglyLinkedListNode?

    init {
        head = null
        tail = null
    }

    public fun insertNode(nodeData: Int) {
        var node = SinglyLinkedListNode(nodeData)

        if (head == null) {
            head = node
        } else {
            tail?.next = node
        }

        tail = node
    }

}
