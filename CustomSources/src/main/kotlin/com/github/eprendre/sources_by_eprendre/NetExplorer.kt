package com.github.eprendre.sources_by_eprendre

import com.github.eprendre.tingshu.extensions.getDesktopUA
import com.github.eprendre.tingshu.extensions.getMobileUA
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object NetExplorer {

    val headers = mutableMapOf(
            "Accept" to "application/json, text/javascript, */*; q=0.01",
            "user-agent" to getMobileUA(),
            "referer" to "https://www.mgting.com/"
    )

    fun config(isDesk: Boolean = false) : NetExplorer {
        if(isDesk){
            headers["user-agent"] = getDesktopUA()
        }else{
            headers["user-agent"] = getMobileUA()
        }
        return NetExplorer
    }

    fun jGet(url:String): Document {
        return Jsoup.connect(url).headers(headers).get()
    }

    fun fGetJson(url:String): JSONObject {
        println("Fuel get Json : $url")
        return Fuel.get(url).header(headers).responseJson().third.get().obj()
    }

    fun fPostJson(url:String, body:List<Pair<String,String>>): JSONObject {
        println("Fuel post Json : $url, body : $body")
        return Fuel.post(url,body).header(headers).responseJson().third.get().obj()
    }

}