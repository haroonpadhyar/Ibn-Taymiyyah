
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
          var str = populateResultTable(data);

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
            $( '#errorMsg' ).show();
            $( '#didYouMean' ).hide();
            $( '#timeDiv' ).hide();
            $( '#qtableDiv' ).hide();
            $( '#paginationDiv' ).hide();
        }else{
            $( '#errorMsg' ).show();
            $( '#didYouMean' ).hide();
            $( '#timeDiv' ).hide();
            $( '#qtableDiv' ).hide();
            $( '#paginationDiv' ).hide();
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
          var str = populateResultTable(data);

          $( '#qtableDiv' ).html( str );
          $( '#timeDiv' ).hide();
          $( '#qtableDiv' ).show();
          $( '#paginationDiv' ).hide();
          $( '#errorMsg' ).hide();
        }else if(resp.code == 300) {
            $( '#errorMsg span' ).html(resp.data);
            $( '#errorMsg' ).show();
            $( '#didYouMean' ).hide();
            $( '#timeDiv' ).hide();
            $( '#qtableDiv' ).hide();
            $( '#paginationDiv' ).hide();
        }else{
            $( '#errorMsg' ).show();
            $( '#didYouMean' ).hide();
            $( '#timeDiv' ).hide();
            $( '#qtableDiv' ).hide();
            $( '#paginationDiv' ).hide();
        }
      }
    });
    return false; // keeps the page from not refreshing
  });
});

var populateResultTable = function(data){
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
    }
    else {
      str += "<p style=\"font-size: large\" dir=\"rtl\">"
          + quran.ayahTranslationText
          + "</p>";
    }
    str += "</div>";
  }
  return str;
}

