<?php
// ��ǰ �˻� ���� php �����Դϴ�.

$con = mysqli_connect("localhost" , "root" , "" , "thesiswork");

if (mysqli_connect_errno($con))
{
    echo "Failed to connect MySQL : " . mysqli_connect_error();
}

mysqli_set_charset($con,"utf8");

$res = mysqli_query($con , "select * from item");

// ####
$result = array();

while ($row = mysqli_fetch_array($res))
{
    array_push($result,
            array('product_name'=>$row[0],'price' => $row[1],'category' => $row[6],
                'maker'=>$row[3] , 'image_link'=>$row[8]
            ));
}
// ####

// !!!! ���� �߰��� / �̹��� ��ũ ����
//$image_link = mysqli_query($con , "select item_link from item");

echo json_encode(array("result" => $result));

mysqli_close($con);

?>