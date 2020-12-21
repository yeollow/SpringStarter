//main의 변수 속성으로 init, save, update, delete를 사용 -> 다른 js파일에서의 init, save 등과 같은 이름을 가진 function과 구분하기 위함
//구분하지 않으면 js function을 덮어쓰게 되므로 유효범위(scope)를 만드는 개념
//main변수 안에서만 init, save, update, delete가 유효함

var index = {

    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },

    save: function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',           //Controller에서 @PostMapping을 사용하였기 때문에 REST규약에 맞게 POST를 사용.
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',            //Controller에서 @PutMapping을 사용하였기 때문에 REST규약에 맞게 PUT을 사용.
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete: function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',          //Controller에서 @DeleteMapping을 사용하였기 때문에 REST규약에 맞게 DELETE를 사용.
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

index.init();