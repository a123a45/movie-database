Traceback (most recent call last):
  File "C:\Users\wucan\AppData\Local\Programs\Python\Python310\lib\runpy.py", line 196, in _run_module_as_main
    return _run_code(code, main_globals, None,
  File "C:\Users\wucan\AppData\Local\Programs\Python\Python310\lib\runpy.py", line 86, in _run_code
    exec(code, run_globals)
  File "C:\Users\wucan\AppData\Local\Programs\Python\Python310\lib\site-packages\markitdown\__main__.py", line 281, in <module>
    main()
  File "C:\Users\wucan\AppData\Local\Programs\Python\Python310\lib\site-packages\markitdown\__main__.py", line 254, in main
    result = markitdown.convert(
  File "C:\Users\wucan\AppData\Local\Programs\Python\Python310\lib\site-packages\markitdown\_markitdown.py", line 306, in convert
    return self.convert_local(source, stream_info=stream_info, **kwargs)
  File "C:\Users\wucan\AppData\Local\Programs\Python\Python310\lib\site-packages\markitdown\_markitdown.py", line 360, in convert_local
    return self._convert(file_stream=fh, stream_info_guesses=guesses, **kwargs)
  File "C:\Users\wucan\AppData\Local\Programs\Python\Python310\lib\site-packages\markitdown\_markitdown.py", line 652, in _convert
    raise UnsupportedFormatException(
markitdown._exceptions.UnsupportedFormatException: Could not convert stream to Markdown. No converter attempted a conversion, suggesting that the filetype is simply not supported.
