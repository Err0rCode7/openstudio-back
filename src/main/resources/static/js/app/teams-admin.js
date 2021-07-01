var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
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
            subjectName: $('#subjectName').val(),
            userNames: [
                $('#userName1').val(),
                $('#userName2').val(),
                $('#userName3').val()
            ]
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/teams/',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('팀이 등록되었습니다.');
            window.location.href = '/admin/teams';
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
            url: '/api/v1/teams/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('팀이 삭제되었습니다.');
            window.location.href = '/admin/teams';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function() {
        var id = $('#id').val();
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/teams/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('팀이 삭제되었습니다.');
            window.location.href = '/admin/teams';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();