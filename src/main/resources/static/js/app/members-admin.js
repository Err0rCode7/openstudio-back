var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete-row').on('click', function () {
            _this.deleteRow($(this));
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            name: $('#name').val(),
            email: $('#email').val(),
            picture: $('#picture').val(),
            role: $('#role').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/members',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('member가 등록되었습니다.');
            window.location.href = '/admin/members';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            name: $('#name').val(),
            email: $('#email').val(),
            picture: $('#picture').val(),
            role: $('#role').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/members/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('member가 수정되었습니다.');
            window.location.href = '/admin/members';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    deleteRow : function(btn) {
        var tr = btn.parent().parent();
        var td = tr.children();
        var id = td.eq(0).text();
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/members/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('member가 삭제되었습니다.');
            window.location.href = '/admin/members';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function() {
        var id = $('#id').val();
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/members/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('member가 삭제되었습니다.');
            window.location.href = '/admin/members';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();