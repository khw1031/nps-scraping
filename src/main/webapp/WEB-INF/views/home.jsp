<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>내 연금 알아보기 스크래핑</title>
    <link rel="stylesheet" href="css/default.css"/>
</head>
<body>
<section>
    <header>
        <h1>1. FSWSS 설치</h1>
    </header>
    <main>
        <p>금융기관 및 기업의 웹 사이트에서 제공되는 다양한 정보를 사용자의 웹 브라우징 없이 자동으로 가져오는 프로그램입니다.</p>
        <button type="button" id="download-fswss">다운로드</button>
    </main>
</section>
<section>
    <header>
        <h1>2. 내 연금 알아보기 연동</h1>
    </header>
    <main>
        <input type="text" name="juminNum" title="주민번호" value="" placeholder="주민등록번호를 입력해주세요."/>
        <div class="banner-cont">
            <h2>좀 더 정확한 금액을 알고 싶으세요?</h2>
            <p>
                국민연금공단 내 연금 알아보기 연동을 통해<br>
                <strong>국민연금, 개인연금, 퇴직연금</strong>을 한 번에 확인할 수 있어요.
            </p>
            <button type="button" id="start-scrap">지금 연동하기</button>
        </div>
    </main>
</section>
<script type="text/javascript" src="/common/js/require-2.3.6.min.js"></script>
<script type="text/javascript" src="/js/onsure.require.js"></script>
<script type="text/javascript">
  require(["jquery", "npsScrap"], function ($, npsScrap) {
    $("#download-fswss").on("click", npsScrap.downloadFSWSS("설치 후에는 반드시 새로고침을 하거나 다시 접속해주세요.", "http://dl.hwlife.hscdn.com/Fintech/FSWSSSetup.exe"));
    $("#start-scrap").on("click", npsScrap.startScrap);
  })
</script>
</body>
</html>
