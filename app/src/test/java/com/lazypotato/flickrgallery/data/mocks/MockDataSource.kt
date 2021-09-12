package com.lazypotato.flickrgallery.data.mocks

import com.lazypotato.flickrgallery.data.model.FlickrFeedResponse
import com.lazypotato.flickrgallery.data.model.FlickrPhoto

object MockDataSource {
    val flickrFeedResponse = FlickrFeedResponse(
        "Uploads from everyone",
        "https:\\/\\/www.flickr.com\\/photos\\/",
        "",
        "2021-09-11T14:37:57Z",
        "https:\\/\\/www.flickr.com",
        listOf(
            FlickrPhoto(
                "Lady president 2019",
                "https:\\/\\/www.flickr.com\\/photos\\/moldgolfclub\\/51445519702\\/",
                FlickrPhoto.Media(
                    "https:\\/\\/live.staticflickr.com\\/65535\\/51445519702_8d6a4b13db_m.jpg"
                ),
                "2021-08-28T14:51:51-08:00",
                " <p><a href=\"https:\\/\\/www.flickr.com\\/people\\/moldgolfclub\\/\">moldgolfclub<\\/a> posted a photo:<\\/p> <p><a href=\"https:\\/\\/www.flickr.com\\/photos\\/moldgolfclub\\/51445519702\\/\" title=\"Lady president 2019\"><img src=\"https:\\/\\/live.staticflickr.com\\/65535\\/51445519702_8d6a4b13db_m.jpg\" width=\"240\" height=\"180\" alt=\"Lady president 2019\" \\/><\\/a><\\/p> <p><\\/p>",
                "2021-09-11T14:37:57Z",
                "nobody@flickr.com (\"moldgolfclub\")",
                "98115452@N06",
                ""
            ),
            FlickrPhoto(
                "Lingering Summer",
                "https:\\/\\/www.flickr.com\\/photos\\/61289118@N08\\/51445520362\\/",
                FlickrPhoto.Media("https:\\/\\/live.staticflickr.com\\/65535\\/51445520362_67752b021c_m.jpg"),
                "2021-08-29T07:10:56-08:00",
                " <p><a href=\"https:\\/\\/www.flickr.com\\/people\\/61289118@N08\\/\">WilsonPhotoGallery<\\/a> posted a photo:<\\/p> <p><a href=\"https:\\/\\/www.flickr.com\\/photos\\/61289118@N08\\/51445520362\\/\" title=\"Lingering Summer\"><img src=\"https:\\/\\/live.staticflickr.com\\/65535\\/51445520362_67752b021c_m.jpg\" width=\"240\" height=\"171\" alt=\"Lingering Summer\" \\/><\\/a><\\/p> <p>Central Ohio in August 2021<\\/p>",
                "2021-09-11T14:38:04Z",
                "nobody@flickr.com (\"WilsonPhotoGallery\")",
                "61289118@N08",
                "sunrise naturephotography landscapephotography"
            ),
            FlickrPhoto(
                "Ama de Casa orgullosa",
                "https:\\/\\/www.flickr.com\\/photos\\/193221484@N04\\/51445520592\\/",
                FlickrPhoto.Media("https:\\/\\/live.staticflickr.com\\/65535\\/51445520592_038b46b851_m.jpg"),
                "2021-09-11T17:38:07-08:00",
                " <p><a href=\"https:\\/\\/www.flickr.com\\/people\\/193221484@N04\\/\">mironovichviktorya<\\/a> posted a photo:<\\/p> <p><a href=\"https:\\/\\/www.flickr.com\\/photos\\/193221484@N04\\/51445520592\\/\" title=\"Ama de Casa orgullosa\"><img src=\"https:\\/\\/live.staticflickr.com\\/65535\\/51445520592_038b46b851_m.jpg\" width=\"196\" height=\"240\" alt=\"Ama de Casa orgullosa\" \\/><\\/a><\\/p> ",
                "2021-09-11T14:38:07Z",
                "nobody@flickr.com (\"mironovichviktorya\")",
                "193221484@N04",
                ""
            )
        )
    )

    val failedFlickrFeedResponse = FlickrFeedResponse(
        "Uploads from everyone",
        "https:\\/\\/www.flickr.com\\/photos\\/",
        "",
        "2021-09-11T14:37:57Z",
        "https:\\/\\/www.flickr.com",
        listOf()
    )
}