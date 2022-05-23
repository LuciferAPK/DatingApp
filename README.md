- OOP :
	+ Tính đóng gói: Tính chất này nhằm bảo vệ đối tượng không bị truy cập từ code bên ngoài
	vào để thay thế giá trị các thuộc tính hay có thể truy cập trực tiếp. Việc cho phép truy cập các
	giá trị của đối tượng tùy theo sự đồng ý của người viết ra lớp của đối tượng đó. Tính chất này
	đảm bảo sự bảo mật, toàn vẹn của đối tượng.

	+ Tính trừu tượng: là một tiến trình chỉ nói ra tính năng của người dùng, các khái niệm được
	định nghĩa trong quá trình phát triển, bỏ qua những chi tiết triển khai bên trong. Tính trừu
	tượng cho phép người lập trình tập trung cốt lỗi cần thiết của đối tượng thay vì quan tâm
	sự phức tạp bên trong hoặc cách nó hoạt động.

	+ Tính kế thừa: cho phép chúng ta cải tiến chương trình bằng cách kế thừa lại lớp cũ và
	phát triển những tính năng mới. Lớp con sẽ kế thừa tất cả những thành phần của lớp cha,
	nhờ sự chia sẻ này mới có thể mở rộng những đặc tính sẵn có mà không cần định nghĩa lại.

	+ Tính đa hình: có thể nói luôn tồn tại song song với tính kế thừa. Khi có nhiều lớp con kế
	thừa lớp cha nhưng có những tính chất khác nhau cũng gọi là đa hình, hoặc những tác vụ 
	trong cùng một đối tượng được thể hiện nhiều cách khác nhau cũng gọi là đa hình. Tính đa 
	hình là kết quả tất yếu khi ta phát triển khả năng kế thừa và nâng cấp chương trình.
	===================================================================
- Class, interface, abstract class, inner class, data type, Override, Overload,...
	+ Class: Người dùng định nghĩa thiết kế cho đối tượng, đại diện cho tập thuộc tính,
	             phương thức chung cho tất cả đối tượng của lớp.
		(Access modifier):
		+ Public: là phạm vi truy cập rộng, có thể truy cập bất cứ đâu trong project.
		+ Private: chỉ cho phép truy cập nội bộ của 1 class.
		+ (Default): Phạm vi mặc định, khi bạn không ghi gì hết thì nó để phạm
			vi truy cập ở dạng này(chỉ nằm trong nội bộ package).
		+ Protected: là phạm vi truy cập có thể từ trong và ngoài package, nhưng 
			phải thông qua tính kế thừa. Protected chỉ có thể áp dụng bên 
			trong class như tuộc tính, phương thức hay lớp con. Không thể
			áp dụng cho lớp ngoài hay interface.
	===================================================================
		(Static): Khai báo 1 biến tĩnh, biến đó có thể lưu thông tin chung cho tất cả
			các đối tượng. (đếm,...). Thông thường biến static sẽ được gọi 
			thông qua tên Class.
		(Khối static): được sử dụng cho mục đích khởi tạo giá trị các biến static.
			Khối sẽ được thực hiện khi lớp chứa nó được load vào bộ nhớ.
			1 lớp có thể có nhiều khối. Các khối này sẽ chạy cùng nhau, chạy
			trước cả chương trình main của lớp đó.
	===================================================================
		(this): dùng để ánh xạ đối tượng hiện tại
	===================================================================
	(Kế thừa trong Java): 
		- Slogan đặc trưng kế thừa: Cha có thì con có, con có thì chưa chắc cha đã có.
	===================================================================
		(Setter & getter): là hai phương thức sử dụng để cập nhật hoặc lấy giá trị của 
			thuộc tính (đặc biệt là thuộc tính ở phạm vi private). Việc sử dụng
			getter và setter cần thiết cho việc sử dụng kiểm soát những thuộc tính	
			quan trọng.
			LƯU Ý: khi đã dùng setter và getter thì thuộc tính nên để private.
	===================================================================
	+ Override (ghi đè): có 2 phương thức giống nhau về tên và tham số truyền vào. Một
		phương thức ở lớp cha, còn cái kia ở lớp con. Override cho phép lớp con có
		thể thực hiện riêng biệt cho phương thức mà lớp cha cung cấp.
		CHÚ Ý: Cách chống Override: thêm từ khóa final
			public final void ...(){ }
	+ Overload: Nhiều phương thức trong cùng một lớp có chung tên nhưng khác tham số
		truyền vào.
	===================================================================
	(Trừu tượng trong OOP):  chỉ nêu ra vấn đề mà không hiển thị cụ thể, chỉ hiện tính năng
		thiết yếu đối với đối tượng người dùng mà không nói quy trình hoạt động.
			+Như vậy, tính trừu tượng là che giấu thông tin thực hiện từ người
			dùng, họ chỉ biết tính năng được cung cấp: Chỉ biết thông tin đối
			tượng thay vì cách sử dụng ntn. Ưu điểm:
				+ Cho phép dev bỏ qua những phức tạp trong đối tượng
				mà chỉ đưa ra những khái niệm phương thức và thuộc
				tính cần biết. Ta sẽ dựa vào những khái niệm đó để	
				viết, nâng cấp, bảo trì.
				+ Giúp ta tập trung cái cốt lõi đối tượng. Giúp người
				dùng không quên bản chất đối tượng đó là gì.
	(Trừu tượng trong Java):
		+ Lớp trừu tượng: là lớp được khai báo mà không thể tạo ra đối tượng từ
		lớp đó. Ta sẽ tạo những lớp con kế thừa lớp trừu tượng. Mục đích lớp trừu
		tượng là tạo ra lớp chung cho những lớp có liên quan với nhau kế thừa.
		+ Phương thức trừu tượng: Các phương thức trừu tượng là chỉ định nghĩa
		mà không có chương trình bên trong, lớp con kế thừa phải bắt buộc override
		nó lại để sử dụng. Có ý nghĩa định nghĩa phương thức bắt buộc phải có trong
		lớp con kế thừa.
	===================================================================
	+ Interface: là một kiểu dữ liệu tham chiếu trong java, nó là tập hợp các phương thức
		abstract. Khi 1 lớp kế thừa interface, nó sẽ kế thừa những phương thức của
		interface đó.
		- (Đặc điểm):
			+ Không thể khởi tạo, nên không có phương thức khởi tạo
			+ Tất cả các phương thức trong interface luôn ở dạng 		
			public abstract mà không cần khai báo
			+ Các thuộc tính trong interface luôn ở dạng public static final
			mà không cần khai báo, yêu cầu phải có giá trị.
		- Mục đích của interface là để thay thế đa kế thừa của những ngôn ngữ khác.
		Ngoài ra, interfăce sẽ giúp đồng bộ và thống nhất trong việc phát triển
		hệ thống trao đổi thông tin.
		(1 class có thể kế thừa nhiều interface).
	===================================================================
- Multi thread, exception handling
	+ Exception handling:  

- Networking: 
	+
