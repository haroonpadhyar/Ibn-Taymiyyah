
$( document ).ajaxStart(function() {
  $("body").addClass("loading");
});

$( document ).ajaxStop(function() {
  $("body").removeClass("loading");
  //$("html,body").animate({scrollTop: 0}, 1000);
});

$( document ).ajaxError(function( event, request, settings ) {
  $( "#loading" ).hide();
});
// Full Text search
$(document).ready(function(){
  $('#nxt,#prv,#orgSrch,#trnsSrch').live('click', function() {

    var src = $( this ).attr( "id" );
    var term = $( '#term' ).val();
    var termHidden = $( '#termHidden' ).val();
    var translator = $( '#translatorCombo' ).val();
    var translatorHidden = $( '#translatorHidden' ).val();
    var currentPage = $( '#currentPageHidden' ).val();
    var original = $( '#originalHidden' ).val();

    if(src == 'orgSrch'){
      original = true;
      currentPage = 1;
    }else if(src == 'trnsSrch'){
      original = false;
      currentPage = 1;
    }else if(src == 'nxt'){
      term = termHidden;
      translator = translatorHidden;
      var pageNo = Number(currentPage);
      currentPage = pageNo + 1;
    }else if(src == 'prv'){
      term = termHidden;
      translator = translatorHidden;
      var pageNo = Number(currentPage);
      if(pageNo > 1) {
        currentPage = pageNo - 1;
      }
    }


    $.ajax({ // ajax call starts
      url: 'search/fulltext',
      type: "POST",
      data: {
        'searchParams': JSON.stringify(
            {
              'term'       : term,
              'translator' : translator,
              'pageNo'     : currentPage,
              'original'   : original
            })
      },
//      dataType: 'json',
      success: function(data)
      {
        var resp = JSON.parse(data);
        if(resp.code == 200) {
          var data = resp.data;
          var str = populateResultTable(data, true);

          $( '#qtableDiv' ).html( str );
          $( '#currentPage' ).html( data.currentPage );
          $( '#totalPages' ).html( data.totalPages );
          $( '#totalHits' ).html( data.totalHits );
          $( '#totalHitsSmall' ).html( data.totalHits );
          $( '#time' ).html( data.time );
          $( '#currentPageHidden' ).val( data.currentPage );
          $( '#totalPagesHidden' ).val( data.totalPages );
          $( '#originalHidden' ).val( data.original );
          $( '#termHidden' ).val( data.term );
          $( '#translatorHidden' ).val( data.translator );

          $( '.nav-tabs' ).show();
          $( '#searchTab' ).show();
          $('.nav-tabs a[href="#Search"]').tab('show');
          var suggestedTerm = data.suggestedTerm;
          if (suggestedTerm.length > 0 ) {
            $( '#didYouMeanSuggestion' ).html( suggestedTerm );
            $( '#didYouMean' ).show();
            $( '#timeDiv' ).hide();
            $( '#qtableDiv' ).hide();
            $( '#paginationDiv' ).hide();
            $( '#errorMsg' ).hide();
          }
          else {
            $( '#didYouMean' ).hide();
            $( '#timeDiv' ).show();
            $( '#qtableDiv' ).show();
            $( '#paginationDiv' ).show();
            $( '#errorMsg' ).hide();
          }
        }else if(resp.code == 300) {
            $( '#errorMsg span' ).html(resp.data);
            displayOnError();
        }else{
            displayOnError();
        }
      }
    });
    return false; // keeps the page from not refreshing
  });
});

//-- Id Search
$(document).ready(function(){
  $('#srch').live('click', function() {

    var param = "";
    var type = $('input:radio[name=radio]:checked').val();
    if(type == 'idSrch'){
      param = $('#surahCombo').val()+'/'+$('#ayaNo').val()+'/'+$( '#translatorCombo' ).val();
    }else if(type == 'srSrch'){
      param = $('#ayaNo').val()+'/'+$( '#translatorCombo' ).val();
    }

    $.ajax({ // ajax call starts
      url: 'search/identity/'+param,
      type: "GET",
//      dataType: 'json',
      success: function(data) {
        var resp = JSON.parse( data );
        if ( resp.code == 200 ) {
          var data = resp.data;
          var str = populateResultTable(data, true);

          $( '.nav-tabs' ).show();
          $( '#searchTab' ).show();
          $('.nav-tabs a[href="#Search"]').tab('show');

          $( '#qtableDiv' ).html( str );
          $( '#totalHitsSmall' ).html( data.totalHits );
          $( '#time' ).html( data.time );
          $( '#timeDiv' ).show();
          $( '#qtableDiv' ).show();
          $( '#paginationDiv' ).hide();
          $( '#errorMsg' ).hide();
        }else if(resp.code == 300) {
          $( '#errorMsg span' ).html(resp.data);
          displayOnError();
        }else{
          displayOnError();
        }
      }
    });
    return false; // keeps the page from not refreshing
  });
});

var displayOnError = function(){
  $( '#errorMsg' ).show();
  $( '#didYouMean' ).hide();
  $( '#timeDiv' ).hide();
  $( '#qtableDiv' ).hide();
  $( '#paginationDiv' ).hide();
}

var populateResultTable = function(data, isRead){
  var readingLable = $('#readingLableHidden' ).html();
  var quranList = data.quranList;
  var str = "";
  for ( var i = 0; i < quranList.length; i++ ) {
    var quran = quranList[i];
    var title = "(" + quran.surahId + ")" + quran.surahName + " " + quran.ayahId + ". " + quran.juzName;
    str += "<div title=\"" + title + "\" class=\"row well\" style=\"margin-right: 0px;margin-left: 0px;\">"
        + "<p style=\"font-size: xx-large\" dir=\"rtl\">"
        + quran.ayahText
        + " . (" + quran.surahName + " " + quran.ayahId + ")"
        + "</p>";

    if ( data.translatorLanguage == "en" ) {
      str += "<p style=\"font-size: large\" dir=\"ltr\">"
          + quran.ayahTranslationText
          + "</p>";
      if(isRead){
        str += " <p><a><span style=\"font-size: small\" dir=\"ltr\" onclick=\"readQuran("+quran.accmId+", 'f');\">"+readingLable+"</span></a></p>";
      }
    }
    else {
      str += "<p style=\"font-size: large\" dir=\"rtl\">"
          + quran.ayahTranslationText
          + "</p>";
      if(isRead){
        str += " <p><a><span style=\"font-size: small\" dir=\"rtl\" onclick=\"readQuran("+quran.accmId+", 'f');\">"+readingLable+"</span></a></p>";
      }
    }
    str += "</div>";
  }
  return str;
}

var readQuran = function(accmId, readDirection){
  $.ajax({ // ajax call starts
    url: 'search/read/'+accmId+'/'+$( '#translatorCombo' ).val() + '/'+readDirection,
    type: "GET",
//      dataType: 'json',
    success: function(data) {
      var resp = JSON.parse( data );
      if ( resp.code == 200 ) {
        var data = resp.data;
        var quranList = data.quranList;
        var str = populateResultTable(data, false);
        if(quranList.length > 0){
          $( '#accumIdHidden' ).val(quranList[0].accmId);
        }

        $( '.nav-tabs' ).show();
        $( '#readTab' ).show();
        $('.nav-tabs a[href="#Read"]').tab('show');
        window.scrollTo(0, 0);

        $( '#qtableReadDiv' ).html( str );
        $( '#totalHitsSmallRead' ).html( data.totalHits );
        $( '#timeRead' ).html( data.time );
        $( '#qtableReadDiv' ).show();
        $( '#paginationReadDiv' ).show();
        $( '#timeReadDiv' ).show();
//        $( '#qtableDiv' ).show();
//        $( '#paginationDiv' ).hide();
//        $( '#errorMsg' ).hide();
      }else if(resp.code == 300) {
        $( '#errorMsg span' ).html(resp.data);
        displayOnError();
      }else{
        displayOnError();
      }
    }
  });
}

//-- Id Search
$(document).ready(function(){
  $('#nxtRead').live('click', function() {
    var accumIdStr = $( '#accumIdHidden' ).val();
    var accumId = Number(accumIdStr) + 11;
    if(accumId > 6236){
      accumId = 6236
    }
    readQuran(accumId, 'f');
    return false; // keeps the page from not refreshing
  });
});

$(document).ready(function(){
  $('#prvRead').live('click', function() {
    var accumIdStr = $( '#accumIdHidden' ).val();
    var accumId = Number(accumIdStr) - 1;
    if(accumId < 1) {
      accumId = 1;
    }
    readQuran(accumId, 'r');
    return false; // keeps the page from not refreshing
  });
});

