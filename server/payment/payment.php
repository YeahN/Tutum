<?php
  $userID = $_GET['userID'];
  $method = $_GET['method'];
  $amount = $_GET['amount'];
  $tel = $_GET['tel'];
?>
<html lang="ko">
<head>
  <meta charset="UTF-8">
</head>
<body>
  <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
  <script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.2.js"></script>

  <div id="userID_div" style="visibility: hidden;"><?php echo $userID; ?></div>
  <div id="method_div" style="visibility: hidden;"><?php echo $method; ?></div>
  <div id="amount_div" style="visibility: hidden;"><?php echo $amount; ?></div>
  <div id="tel_div" style="visibility: hidden;"><?php echo $tel; ?></div>

  <script type="text/javascript">
    window.onload = function() {
      var IMP = window.IMP; // 생략가능
      IMP.init('imp55000669'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용

      var userID = document.getElementById("userID_div").innerHTML;
      var method = document.getElementById("method_div").innerHTML;
      var amount = document.getElementById("amount_div").innerHTML;
      var tel = document.getElementById("tel_div").innerHTML;

      var json = {
        pg: 'html5_inicis',
        pay_method: method,
        name: 'TUTUM',
        amount: amount,
        buyer_tel: tel,
        m_redirect_url: 'http://13.59.135.92/payment/complete.php?userID=' + userID,
        app_scheme: 'tutumandroid'
      };
      IMP.request_pay(json);
    }
  </script>
</body>
</html>
