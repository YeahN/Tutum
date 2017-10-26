<?php
  ob_start();

  include('/var/www/html/db.php');
  require_once('iamport.php');

  $userID = $_GET['userID'];
  $imp_uid = $_GET["imp_uid"];

  $iamport = new Iamport('1596207647774135', '1mF7OPDZqBGFGph5c0QE7deEJiEMXgHYCn5qdJqDl7i2xA7KBYpIcEmVX1IUhqSTNYk1HLE3SX5qxda8');
  $rsp = $iamport->findByImpUID($imp_uid);

  if($rsp->success) {
    $payment_data = $rsp->data;
    $time = gmdate('Y-m-d h:i:s', $payment_data->paid_at);

    if($payment_data->status === 'paid') {
      $query = "INSERT INTO pay_history(userID, sign, amount, detail, time) VALUES('".$userID."', '+', '".$payment_data->amount."', '".$payment_data->pay_method."', '".$time."');";
      $result1 = mysqli_query($conn, $query);

      $query = "UPDATE users SET point = point + $payment_data->amount WHERE id = '".$userID."';";
      $result2 = mysqli_query($conn, $query);

      if($result1 && $result2) {
              echo "success";
      } else echo "fail";
      mysqli_close($conn);
    }
  } else {
    error_log($rsp->error['code']);
    error_log($rsp->error['message']);
  }
?>
<html>
<body>
  <a id="tutumlink" href="tutumredirect://success"></a>
  <script>
    window.onload = function() {
      document.getElementById('tutumlink').click();
    }
  </script>
</body>
</html>
