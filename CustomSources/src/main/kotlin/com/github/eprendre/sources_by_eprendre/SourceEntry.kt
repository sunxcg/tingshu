package com.github.eprendre.sources_by_eprendre

import com.github.eprendre.tingshu.sources.TingShu

object SourceEntry {

    /**
     * 说明
     */
    @JvmStatic
    fun getDesc(): String {
        return "内置源"
    }

    /**
     * 返回此包下面的源
     */
    @JvmStatic
    fun getSources(): List<TingShu> {
        return listOf(
            HuanTingWang,
            JDLG,
            YouTuYueDu,
            YunTuYouSheng,
            WoTingPingShu,
            KuWo,
            KouDaiWeiKeTang,
            BoKanYouSheng,
            ZhongBanYouSheng,
            CCTV,
            YouShengXiaoShuoBa,
            ShengBoFM,
            IFish,
            TingChina,
            BiliBili,
            JingTing,
            XinMo,
            TingShu74,
            TingShuBao,
            //            MaLaTIngShu
//                YouYuKu,
//                HaiDao,
            SouGou,
            Mgting
        )
    }
}