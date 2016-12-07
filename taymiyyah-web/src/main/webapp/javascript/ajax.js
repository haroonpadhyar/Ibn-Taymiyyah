
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
    $.ajax({ // ajax call starts
      url: 'search/fulltext',
      type: "POST",
      data: {
        'searchParams': JSON.stringify(
            {
              'term'       : $( '#term' ).val(),
              'translator' : $( '#translatorCombo' ).val(),
              'currentPage': $( '#currentPageHidden' ).val(),
              'original'   : $( '#originalHidden' ).val()
            })
      },
//      dataType: 'json',
      success: function(data)
      {
        var resp = JSON.parse(data);
        if(resp.code == 200) {
          var data = resp.data;
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

          var suggestedTerm = data.suggestedTerm;
          if (suggestedTerm.length > 0 ) {
            $( '#didYouMeanSuggestion' ).html( suggestedTerm );
            $( '#didYouMean' ).show();
            $( '#timeDiv' ).hide();
            $( '#qtableDiv' ).hide();
            $( '#paginationDiv' ).hide();
          }
          else {
            $( '#didYouMean' ).hide();
            $( '#timeDiv' ).show();
            $( '#qtableDiv' ).show();
            $( '#paginationDiv' ).show();
          }
        }else{
          //TODO show error msg.
        }
      }
    });
    return false; // keeps the page from not refreshing
  });
});

//-- Id Search
$(document).ready(function(){
  $('#srch').live('click', function() {
    $.ajax({ // ajax call starts
      url: 'search/identity/'+$('#ayahCombo').val()+'/'+$('#ayaNo').val()+'/'+$( '#translatorCombo' ).val(),
      type: "GET",
//      dataType: 'json',
      success: function(data) {
        var resp = JSON.parse( data );
        if ( resp.code == 200 ) {
          var data = resp.data;
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

          $( '#qtableDiv' ).html( str );
          $( '#timeDiv' ).hide();
          $( '#qtableDiv' ).show();
          $( '#paginationDiv' ).hide();
        }
      }
    });
    return false; // keeps the page from not refreshing
  });
});

