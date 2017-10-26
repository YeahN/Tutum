<?php
  ob_start();
  include('/var/www/html/db.php');

  $id = $_GET['id'];
  $pw = $_GET['pw'];

  $hash = password_hash($pw, PASSWORD_BCRYPT);

  $query = "SELECT * FROM users WHERE id = '".$id."';";
  $result = mysqli_query($conn, $query);
  $row = mysqli_fetch_array($result);
  if($row) {
    if(password_verify($pw, $row['password'])) {
      echo "success";
    } else echo "fail";
  } else echo "fail";

  mysqli_close($conn);
?>
