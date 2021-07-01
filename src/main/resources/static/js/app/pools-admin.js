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
            _this.delete($(this));
        });
    },
    save : function () {
        var data = {
            name: $('#name').val(),
            description: $('#description').val(),
            circle: $('#circle').val(),
            pdfRef: $('#pdfRef').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/pools/',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('pool이 등록되었습니다.');
            window.location.href = '/admin/pools';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            name: $('#name').val(),
            description: $('#description').val(),
            circle: $('#circle').val(),
            pdfRef: $('#pdfRef').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/pools/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('pool이 수정되었습니다.');
            window.location.href = '/admin/pools';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function(btn) {
        var tr = btn.parent().parent();
        var td = tr.children();
        var id = td.eq(0).text();
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/pools/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('pool이 삭제되었습니다.');
            window.location.href = '/admin/pools';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();