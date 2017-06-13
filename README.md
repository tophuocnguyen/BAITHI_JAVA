### Bài thi môn lập trình Java
### Họ Tên: Tô Phước Nguyên
### Mã số sv: 56131368 
### Lớp: 56TH1
### Giáo viên hướng dẫn: Mai Cường Thọ
# ỨNG DỤNG  GAME BẦU CUA TÔM CÁ
_____________________________________________________

#### `1. Giới Thiệu`
Ngày nay các game dân gian được xây dựng thành các ứng dụng trên điện thoại đã không còn xa lạ và game bầu cua tôm cá là một trong số đó, game giúp người chơi giải trí sau những giời làm việc căng thẳng.

#### `2. Giao diện home`
![Imgur](http://i.imgur.com/hlN4tsD.jpg)
* gồm 2 nút ***chơi ngay*** và ***hướng dẫn***
* Nút ***chơi ngay*** sẽ đưa người dùng đến giao diện chính của game
* Nút ***hướng dẫn*** đưa người đến giao diện hướng dẫn chơi game
#### `3. Giao diện chính`
![Imgur](http://i.imgur.com/Wy2gzdp.jpg)
giao diện gồm có:
* Một ô hiển thị tiền hiện có của người chơi
* Một ô hiện thi thời gian
* Một nút lắc xí ngầu
* Một nút tắt bật âm thanh
* Một nút quay về màn hình chính
* Ba ô xí ngầu
* Sáu ô dành để đặt cược

#### `2. Chế độ chơi` 
* Khi mới bắt đầu người chơi sẽ được 1000 khởi điểm, có thể đặt cược vào các ô bầu, cua, tôm, cá, nai, gà với mức tiền từ 0 đến 500, nếu đặt trúng ô giống xí ngầu vừa  quay được người chơi sẽ được công số tiền tương ứng với số tiền họ đã đặt và ngược lại họ sẽ bị trừ tiền nếu ô họ đặt không giống. Người chơi sẽ không thể đặt cược nếu số tiền họ đang có bé hơn số tiền đặt cược ở các ô bầu, cua, tôm, cá, nai, gà. 
#### `3. các chức năng`
* Nút bật tắt nhạc nền: khi người dùng ấn vào bản nhạc sẽ dừng lại và khi ấn một lần nữa thì bản nhạc sẽ chạy ngay tại thời điểm dừng. 
* Ba nút xí ngầu được quay ngẫu nhiên trong vòng 1s khi ấn nút ***bắt đầu***
* Các ô đặt cược Khi ấn vào ô đặt cược bất kỳ sẽ hiện ra một drop list từ 1 đến 500
* Ô hiện thị thời gian: đếm thời gian người chơi đã chơi đồng thời mỗi khi trôi qua 3 phút người chơi sẽ được cộng thêm 1000 vào tổng tiền của họ. 
#### `4. Giao diện hướng dẫn `
![Imgur](http://i.imgur.com/RmRPihl.jpg)
* hướng dẫn người chơi cách chơi game bầu cua
