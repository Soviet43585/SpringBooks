$(document).ready(function () {
  let beforeChanges;
  let id;
  $('[contenteditable]').click(function (event) {
    beforeChanges = event.target.innerText;
    let $parent = $(this).closest("tr");
    id = $parent.attr("data-id");
  });

  $('[contenteditable]').focusout(function (event) {
    let changes = event.target.innerText;
    if (beforeChanges !== changes) {
      $.ajax({
        url: '/edit',
        type: 'PUT',
        data: {id, beforeChanges, changes}
      });
    }
  });

  $('[contenteditable]').keydown(function (event) {
    const key = event.key;
    if (key === "Delete") {
      $.ajax({
        url: '/deletebook',
        type: 'DELETE',
        data: {id: id},
        success: function () {
          location.reload();
        }
      });

    }
  });

  $(".container tr").click(function () {
    $(this).addClass('selected').siblings().removeClass('selected');
  });
})