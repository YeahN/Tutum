<?php
  ob_start();
  include('/var/www/html/db.php');

  $id = $_GET['id'];
  $query = "SELECT name, point FROM users WHERE id ='".$id."';";
  $result= mysqli_query($conn, $query);
  $row = mysqli_fetch_array($result);

  echo json_encode($row);
  mysqli_close($conn);
?>
