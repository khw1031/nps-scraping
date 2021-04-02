package com.github.khw1031.npsscrap.service;

import btworks.wskit.server.crypto.WCryptoKA;
import com.github.khw1031.npsscrap.domain.DataSet;

public class SecureService {
    public DataSet getEncParam(final DataSet input) throws Exception {
        DataSet result = new DataSet();
        String encKeyForJuminNum = input.getText("encKeyForJuminNum");
        String juminNum = input.getText("juminNum");

        WCryptoKA wcrypto = new WCryptoKA();
        wcrypto.setEncoding("EUC-KR");
        String decodedKeyForJuminNum = wcrypto.decrypt(encKeyForJuminNum);
        String encodedJuminNum = "";

        if (!"".equals(juminNum)) {
            encodedJuminNum = wcrypto.enryptByKey(decodedKeyForJuminNum, juminNum);
        }

        result.put("encodedJuminNum", encodedJuminNum);

        return result;
    }

    public DataSet decScrapData(final DataSet input) {

        DataSet result = new DataSet();

        // 서버 인증서는 로컬에서 로드
        String encKey = input.getText("encKey");
        String encData = input.getText("encData");

        WCryptoKA wcrypto = new WCryptoKA();
        wcrypto.setEncoding("EUC-KR");
        String decodedKey = wcrypto.decrypt(encKey);
        String decodedData = "";

        if (!"".equals(encData)) {
            decodedData = wcrypto.decryptByKey(decodedKey, encData);
        }
        result.put("decodedData", decodedData);
        return result;
    }
}
