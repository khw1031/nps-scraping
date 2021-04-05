define(["jquery"], function ($) {

  var HOST = "https://local.flyhigh-x.com:52177/";
  var SERVICE_KEY = "9b202c53792445e58a7b88ce896365ac"
  var SERVICE_ID = "s9110104"
  var URLs = {
    updateEngine: HOST + "UpdateEngine?service_key=" + SERVICE_KEY + "&sid=" + SERVICE_ID,
    getRandomKey: HOST + "getRandomKey?service_key=" + SERVICE_KEY + "&sid=" + SERVICE_ID,
    scrap: HOST + "ScrapService?service_key=" + SERVICE_KEY + "&sid=" + SERVICE_ID + "&timeout=300",
    encParam: "/scraping/encParam",
    decrypt: "/scraping/decrypt",
  }

  // COMMON
  function logError(stepDesc) {
    return function (e) {
      console.error("[" + stepDesc + "] --> ", e);
      if (stepDesc === "updateEngine") {
        downloadFSWSS("연동 프로그램 설치가 필요합니다. 설치 후에는 반드시 새로고침을 하거나 다시 접속해주세요.")
      }
    }
  }

  // STEP 0: FSWSS 모듈 다운로드 필요
  function downloadFSWSS(noticePhrase, url) {
    // TODO: 온슈어 모듈 링크 필요(CDN)
    return function () {
      alert(noticePhrase || "설치 후에는 반드시 새로고침을 하거나 다시 접속해주세요.")
      url = url || "http://dl.hwlife.hscdn.com/Fintech/FSWSSSetup.exe";
      window.open(url);
    }
  }


  // STEP1: 엔진 업데이트 확인 / FSWSS 모듈 설치 확인
  function startScrap() {
    $.ajax({
      url: URLs.updateEngine,
      success: getRandomKey,
      error: logError("updateEngine"),
    })
  }

  // STEP2: getRandomKey 요청 - 주민번호 암호화 위한 encKey, scrap 요청을 위한 sessionKey 받아옴
  function getRandomKey() {
    $.ajax({
      url: URLs.getRandomKey,
      crossDomain: true,
      cache: false,
      success: encParam,
      error: logError("getRandomKey"),
    })
  }

  // STEP3: encParam - 주민번호 암호화
  function encParam(data) {
    if (data.indexOf("sessionKey") === -1) return;
    console.log(data);
    var json = JSON.parse(data);
    var sessionKey = json.sessionKey;
    var encKeyForJuminNum = decodeURIComponent(json.encKey);
    var juminNum = document.querySelector("[name='juminNum']").value;

    $.ajax({
      method: "POST",
      url: URLs.encParam,
      dataType: "json",
      contentType: "application/json",
      data: JSON.stringify({
        encKeyForJuminNum: encKeyForJuminNum,
        juminNum: juminNum,
      }),
      success: scrap(sessionKey),
      error: logError("encParam")
    })
  }

  function scrap(sessionKey) {
    return function(data) {
      var encodedJuminNum = data.encodedJuminNum;
      $.ajax({
        url: URLs.scrap + "&sessionKey=" + sessionKey + "&family_jumin=" + encodedJuminNum,
        crossDomain: true,
        success: decrypt,
        error: logError("scrap")
      })
    }
  }

  function decrypt(data) {
    var json = JSON.parse(data);
    var encKey = decodeURIComponent(json.encKey);
    var encData = decodeURIComponent(json.encData);
    $.ajax({
      method: "POST",
      url: URLs.decrypt,
      dataType: "json",
      contentType: "application/json",
      data: JSON.stringify({
        encKey: encKey,
        encData: encData,
      }),
      success: getDecodedData,
      error: logError("decrypt")
    })
  }

  function getDecodedData(data) {
    console.log(data.decodedData);
  }

  return {
    downloadFSWSS: downloadFSWSS,
    startScrap: startScrap,
  };
});
