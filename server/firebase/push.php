<?php
  include('/var/www/html/db.php');
  $invoice = $_GET['invoice'];
  $userId = '';
  $toke n= '';

  $sql = "select userID from invoices where invoiceNo = '".$invoice."';";
  $result = mysqli_query($conn, $sql);
  $row= mysqli_fetch_assoc($result);
  if($row != null) {
    $userId = $row['userID'];
    echo "userID: $userId";
  } else echo "db connect fail";

  $sql = "select Token from fcm where userId= '".$userId."' and valid ='y';";
  $result = mysqli_query($conn, $sql);
  $row= mysqli_fetch_assoc($result);
  if($row != null) {
    $token = $row['Token'];
    echo "token: $token";
    mysqli_close($conn);

    $url = 'https://fcm.googleapis.com/fcm/send';
    $api_key = 'AAAAOQtucFw:APA91bFtPu9vLG-PyokAf6vj-H0UMAL5qGiZqmyJQAU4eWOczKYI39GzEfJhGgARL3sv7biCvXSZkeM_xU_BCY-RofLj2IUHECpLvLLRc1Suu5CsmsKC6rJnMZulfPR2JEysJbnICs8a';
    $msg = array(
      'body'  => "배송완료",
      'title' => 'Tutum',
    );
    $fields = array(
      'to' => $token,
      'notification'  => $msg
    );
    $headers = array(
      'Authorization: key=' . $api_key,
      'Content-Type: application/json'
    );

    $ch = curl_init();
    curl_setopt( $ch,CURLOPT_URL, $url );
    curl_setopt( $ch,CURLOPT_POST, true );
    curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
    curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
    curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
    curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
    $result = curl_exec($ch );
    curl_close( $ch );
    echo $result;
  }
?>
