<?php
  include ('/var/www/html/db.php');

  $token = $_GET["Token"];
  $userId = $_GET["userId"];

  $query = "insert into fcm(Token, userId) values ('".$token."', '".$userId."') on duplicate key update Token = '".$token."';";
  $result = mysqli_query($conn, $query);

  if($result) {
    echo "success";
  } else echo "fai";

  mysqli_close($conn);
?>
